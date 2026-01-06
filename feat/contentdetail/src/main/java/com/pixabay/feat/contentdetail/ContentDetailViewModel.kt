package com.pixabay.feat.contentdetail

import androidx.lifecycle.viewModelScope
import com.pixabay.core.common.base.BaseViewModel
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.domain.usecase.AddFavoriteImageUseCase
import com.pixabay.core.domain.usecase.AddFavoriteVideoUseCase
import com.pixabay.core.domain.usecase.datastore.image.IsFavoriteImageUseCase
import com.pixabay.core.domain.usecase.IsFavoriteVideoUseCase
import com.pixabay.core.domain.usecase.RemoveFavoriteImageUseCase
import com.pixabay.core.domain.usecase.RemoveFavoriteVideoUseCase
import com.pixabay.feat.contentdetail.model.ContentInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    private val isFavoriteVideoUseCase: IsFavoriteVideoUseCase,
    private val isFavoriteImageUseCase: IsFavoriteImageUseCase,
    private val addFavoriteVideoUseCase: AddFavoriteVideoUseCase,
    private val addFavoriteImageUseCase: AddFavoriteImageUseCase,
    private val removeFavoriteVideoUseCase: RemoveFavoriteVideoUseCase,
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
) : BaseViewModel<ContentDetailContract.ContentDetailState, ContentDetailContract.Event, ContentDetailContract.SideEffect>() {

    override val _state = MutableStateFlow(ContentDetailContract.ContentDetailState())

    override suspend fun handleEvent(event: ContentDetailContract.Event) {
    }

    fun loadContent(
        video: VideoSearch? = null,
        image: ImageSearch? = null
    ) {
        viewModelScope.launch {
            runCatching {
                val contentInfo = video?.let { ContentInfo.fromVideo(it) }
                    ?: image?.let { ContentInfo.fromImage(it) }
                    ?: ContentInfo()

                val isFavorite = checkIsFavorite(video, image)

                setContent(
                    contentInfo = contentInfo,
                    video = video,
                    image = image,
                    isFavorite = isFavorite
                )
            }.onFailure { e ->
                handleError(e)
            }.also {
                setLoading(false)
            }
        }
    }

    fun onOffFavorite() {
        viewModelScope.launch {
            runCatching {
                state.value.video?.let { video ->
                    toggleVideoFavorite(video)
                }

                state.value.image?.let { image ->
                    toggleImageFavorite(image)
                }
            }.onFailure { e ->
                handleError(e)
            }
        }
    }

    /**
     * 즐겨찾기 상태 확인 UseCase 호출
     */
    private suspend fun checkIsFavorite(
        video: VideoSearch?,
        image: ImageSearch?
    ): Boolean {
        return video?.let { isFavoriteVideoUseCase(it.id) }
            ?: image?.let { isFavoriteImageUseCase(it.id) }
            ?: false
    }

    /**
     * 비디오 즐겨찾기 토글 UseCase 호출
     */
    private suspend fun toggleVideoFavorite(video: VideoSearch) {
        if (isFavoriteVideoUseCase(video.id)) {
            removeFavoriteVideoUseCase(video.id)
            setFavoriteStatus(false)
        } else {
            addFavoriteVideoUseCase(video)
            setFavoriteStatus(true)
        }
    }

    /**
     * 이미지 즐겨찾기 토글 UseCase 호출
     */
    private suspend fun toggleImageFavorite(image: ImageSearch) {
        if (isFavoriteImageUseCase(image.id)) {
            removeFavoriteImageUseCase(image.id)
            setFavoriteStatus(false)
        } else {
            addFavoriteImageUseCase(image)
            setFavoriteStatus(true)
        }
    }

    private fun setLoading(loading: Boolean) {
        reduce { copy(loading = loading) }
    }

    private fun setContent(
        contentInfo: ContentInfo,
        video: VideoSearch? = null,
        image: ImageSearch? = null,
        isFavorite: Boolean
    ) {
        reduce {
            copy(
                loading = false,
                contentInfo = contentInfo,
                video = video,
                image = image,
                isFavorite = isFavorite
            )
        }
    }

    private fun setFavoriteStatus(isFavorite: Boolean) {
        reduce { copy(isFavorite = isFavorite) }
    }
}