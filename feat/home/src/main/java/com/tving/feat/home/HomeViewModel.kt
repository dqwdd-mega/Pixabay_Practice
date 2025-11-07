package com.tving.feat.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tving.core.common.base.BaseViewModel
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.usecase.AddFavoriteVideoUseCase
import com.tving.core.domain.usecase.GetFavoriteVideosUseCase
import com.tving.core.domain.usecase.GetSearchImageUseCase
import com.tving.core.domain.usecase.GetSearchVideoUseCase
import com.tving.core.domain.usecase.IsFavoriteVideoUseCase
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
) : BaseViewModel<HomeContract.HomeState, HomeContract.Event, HomeContract.SideEffect>() {

    override val _state = MutableStateFlow(HomeContract.HomeState())

    init {
        getFavoriteVideos()
    }

    override suspend fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.ClickSearch -> searchContent()
            is HomeContract.Event.ClickOnOffVideoFavorite -> onOffVideoFavorite()
        }
    }

    private fun getFavoriteVideos() {
        getFavoriteVideosUseCase()
            .onEach { favoriteVideos ->
                reduce { copy(favoriteVideos = favoriteVideos) }
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
            try {
                updateLoading(true)
                resetImagePage()

                val videoResult = getSearchVideoUseCase(query = searchText)
                val imageResult = getSearchImageUseCase(query = searchText, page = 1, perPage = state.value.perPage)

                val hasResults = videoResult.data.isNotEmpty() || imageResult.data.isNotEmpty()

                if (hasResults) {
                    updateSearchState(SearchState.Success)
                    handleVideoResults(videoResult.data)
                    handleImageResults(imageResult.data, isAppend = false)
                    updateTotalImageHits(imageResult.totalHits)
                } else {
                    updateSearchState(SearchState.Empty)
                }
            } catch (e: Exception) {
                Log.e("tetest", "tetest, Exception === ${e.message}", e)
                updateSearchState(SearchState.Fail)
            } finally {
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
            try {
                updateSearchImagePagingLoading(true)
                
                val nextPage = currentState.currentImagePage + 1
                val result = getSearchImageUseCase(
                    query = searchText, 
                    page = nextPage,
                    perPage = currentState.perPage
                )

                if (result.data.isNotEmpty()) {
                    handleImageResults(result.data, isAppend = true)
                    reduce { copy(currentImagePage = nextPage) }
                }

            } catch (e: Exception) {
                Log.e("tetest", "tetest, loadMore Exception === ${e.message}", e)
                e.printStackTrace()
            } finally {
                updateSearchImagePagingLoading(false)
            }
        }
    }

    private fun handleVideoResults(videos: List<VideoSearch>) {
        if (videos.isNotEmpty()) {
            reduce { copy(firstVideo = videos.first()) }
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

    /**
     * 검색 시작 시 페이지 리셋
     */
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
            if (isFavoriteVideoUseCase(video.id)) {
                removeFavoriteVideoUseCase(video.id)
            } else {
                addFavoriteVideoUseCase(video)
            }
        }
    }
}