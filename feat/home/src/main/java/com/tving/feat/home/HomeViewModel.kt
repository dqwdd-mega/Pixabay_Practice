package com.tving.feat.home

import androidx.lifecycle.viewModelScope
import com.tving.core.common.base.BaseViewModel
import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.usecase.AddFavoriteImageUseCase
import com.tving.core.domain.usecase.AddFavoriteVideoUseCase
import com.tving.core.domain.usecase.GetFavoriteImagesUseCase
import com.tving.core.domain.usecase.GetFavoriteVideosUseCase
import com.tving.core.domain.usecase.GetSearchImageUseCase
import com.tving.core.domain.usecase.GetSearchVideoUseCase
import com.tving.core.domain.usecase.IsFavoriteImageUseCase
import com.tving.core.domain.usecase.IsFavoriteVideoUseCase
import com.tving.core.domain.usecase.RemoveFavoriteImageUseCase
import com.tving.core.domain.usecase.RemoveFavoriteVideoUseCase
import com.tving.feat.home.model.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSearchVideoUseCase: GetSearchVideoUseCase,
    private val getSearchImageUseCase: GetSearchImageUseCase,
    private val getFavoriteVideosUseCase: GetFavoriteVideosUseCase,
    private val isFavoriteVideoUseCase: IsFavoriteVideoUseCase,
    private val addFavoriteVideoUseCase: AddFavoriteVideoUseCase,
    private val removeFavoriteVideoUseCase: RemoveFavoriteVideoUseCase,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val isFavoriteImageUseCase: IsFavoriteImageUseCase,
    private val addFavoriteImageUseCase: AddFavoriteImageUseCase,
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
) : BaseViewModel<HomeContract.HomeState, HomeContract.Event, HomeContract.SideEffect>() {

    override val _state = MutableStateFlow(HomeContract.HomeState())

    init {
        getFavoriteVideos()
        getFavoriteImages()
    }

    override suspend fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.ClickSearch -> searchContent()
            is HomeContract.Event.ClickOnOffVideoFavorite -> onOffVideoFavorite()
            is HomeContract.Event.ClickVideoContent -> {
                postSideEffect(
                    HomeContract.SideEffect.NavigateToContentDetailWithVideo(event.video)
                )
            }
            is HomeContract.Event.ClickImageContent -> {
                postSideEffect(
                    HomeContract.SideEffect.NavigateToContentDetailWithImage(event.image)
                )
            }
        }
    }

    private fun getFavoriteVideos() {
        getFavoriteVideosUseCase()
            .onEach { favoriteVideos ->
                reduce {
                    val currentFirstVideo = firstVideo
                    val updatedIsFirstVideoFavorite = currentFirstVideo?.let { video ->
                        favoriteVideos.any { it.id == video.id }
                    } ?: false

                    copy(
                        favoriteVideos = favoriteVideos,
                        isFirstVideoFavorite = updatedIsFirstVideoFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getFavoriteImages() {
        getFavoriteImagesUseCase()
            .onEach { favoriteImages ->
                reduce { copy(favoriteImages = favoriteImages) }
            }
            .launchIn(viewModelScope)
    }

    /**
     * 통합 검색 (비디오 + 이미지)
     */
    fun searchContent() {
        val searchText = state.value.searchText
        if (searchText.isEmpty()) return

        viewModelScope.launch {
            runCatching {
                updateLoading(true)
                resetVideoPage()
                resetImagePage()

                val videoResult = getSearchVideo(searchText)
                val imageResult = getSearchImage(
                    query = searchText,
                    page = 1,
                    perPage = state.value.perPage
                )

                val videos = videoResult.data
                val images = imageResult.data
                val totalImageHits = imageResult.totalHits

                val hasResults = videos.isNotEmpty() || images.isNotEmpty()

                if (hasResults) {
                    updateSearchState(SearchState.Success)
                    handleVideoResults(videos)
                    handleImageResults(images, isAppend = false)
                    updateTotalImageHits(totalImageHits)
                } else {
                    updateSearchState(SearchState.Empty)
                }
            }.onFailure { e ->
                updateSearchState(SearchState.Fail)
                handleError(e)
            }.also {
                updateLoading(false)
            }
        }
    }

    /**
     * 이미지 검색 (페이징 적용)
     */
    fun searchImages() {
        val searchText = state.value.searchText
        if (searchText.isEmpty()) return
        if (state.value.searchImagePagingLoading) return

        val currentState = state.value
        val loadedImagesCount = currentState.images.size
        if (loadedImagesCount >= currentState.totalImageHits) return

        viewModelScope.launch {
            runCatching {
                updateSearchImagePagingLoading(true)

                val nextPage = currentState.currentImagePage + 1
                val result = getSearchImage(
                    query = searchText,
                    page = nextPage,
                    perPage = currentState.perPage
                )

                if (result.data.isNotEmpty()) {
                    handleImageResults(result.data, isAppend = true)
                    reduce { copy(currentImagePage = nextPage) }
                }
            }.onFailure { e ->
                handleError(e)
            }.also {
                updateSearchImagePagingLoading(false)
            }
        }
    }

    private fun handleVideoResults(videos: List<VideoSearch>) {
        if (videos.isNotEmpty()) {
            val video = videos.first()
            val currentFavoriteVideos = state.value.favoriteVideos

            val isFavorite = currentFavoriteVideos.any { it.id == video.id }

            reduce {
                copy(
                    firstVideo = video,
                    isFirstVideoFavorite = isFavorite
                )
            }
        }
    }

    private fun handleImageResults(images: List<ImageSearch>, isAppend: Boolean = false) {
        if (isAppend) {
            // 기존 리스트에 추가
            val currentImages = state.value.images
            reduce { copy(images = currentImages + images) }
        } else {
            // 새로운 리스트로 교체
            reduce { copy(images = images) }
        }
    }

    private fun updateLoading(loading: Boolean) {
        reduce { copy(loading = loading) }
    }

    private fun updateSearchState(searchState: SearchState) {
        reduce { copy(searchState = searchState) }
    }

    fun updateSearchText(text: String) {
        reduce { copy(searchText = text) }

        if (text.isEmpty()) {
            updateShowSearchRightContent(false)
            updateSearchState(SearchState.Idle)
        } else {
            updateShowSearchRightContent(true)
        }
    }

    private fun updateShowSearchRightContent(show: Boolean) {
        reduce { copy(showSearchRightContent = show) }
    }

    private fun resetVideoPage() {
        reduce { copy(firstVideo = null, isFirstVideoFavorite = false) }
    }

    private fun resetImagePage() {
        reduce { copy(currentImagePage = 1, images = emptyList(), totalImageHits = 0) }
    }

    private fun updateSearchImagePagingLoading(loading: Boolean) {
        reduce { copy(searchImagePagingLoading = loading) }
    }

    private fun updateTotalImageHits(totalHits: Int) {
        reduce { copy(totalImageHits = totalHits) }
    }

    fun onOffVideoFavorite() {
        val video = state.value.firstVideo ?: return

        viewModelScope.launch {
            runCatching {
                onOffVideoFavoriteUseCase(video)
            }.onFailure { e ->
                handleError(e)
            }
        }
    }

    fun onOffImageFavorite(image: ImageSearch) {
        viewModelScope.launch {
            runCatching {
                onOffImageFavoriteUseCase(image)
            }.onFailure { e ->
                handleError(e)
            }
        }
    }

    private suspend fun getSearchVideo(query: String): BaseResult<List<VideoSearch>> {
        return getSearchVideoUseCase(query = query)
    }

    private suspend fun getSearchImage(
        query: String,
        page: Int,
        perPage: Int
    ): BaseResult<List<ImageSearch>> {
        return getSearchImageUseCase(
            query = query,
            page = page,
            perPage = perPage
        )
    }

    private suspend fun onOffVideoFavoriteUseCase(video: VideoSearch) {
        if (isFavoriteVideoUseCase(video.id)) {
            removeFavoriteVideoUseCase(video.id)
        } else {
            addFavoriteVideoUseCase(video)
        }
    }

    private suspend fun onOffImageFavoriteUseCase(image: ImageSearch) {
        if (isFavoriteImageUseCase(image.id)) {
            removeFavoriteImageUseCase(image.id)
        } else {
            addFavoriteImageUseCase(image)
        }
    }
}