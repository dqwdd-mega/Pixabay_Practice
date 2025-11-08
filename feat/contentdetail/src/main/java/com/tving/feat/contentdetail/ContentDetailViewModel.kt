package com.tving.feat.contentdetail

import androidx.lifecycle.viewModelScope
import com.tving.core.common.base.BaseViewModel
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.usecase.AddFavoriteImageUseCase
import com.tving.core.domain.usecase.AddFavoriteVideoUseCase
import com.tving.core.domain.usecase.IsFavoriteImageUseCase
import com.tving.core.domain.usecase.IsFavoriteVideoUseCase
import com.tving.core.domain.usecase.RemoveFavoriteImageUseCase
import com.tving.core.domain.usecase.RemoveFavoriteVideoUseCase
import com.tving.feat.contentdetail.model.ContentInfo
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
            setLoading(true)

            val contentInfo = video?.let { ContentInfo.fromVideo(it) }
                ?: image?.let { ContentInfo.fromImage(it) }
                ?: ContentInfo()

            val isFavorite = video?.let { isFavoriteVideoUseCase(it.id) }
                ?: image?.let { isFavoriteImageUseCase(it.id) }
                ?: false

            setContent(
                contentInfo = contentInfo,
                video = video,
                image = image,
                isFavorite = isFavorite
            )

            setLoading(false)
        }
    }

    fun onOffFavorite() {
        viewModelScope.launch {
            state.value.video?.let { video ->
                onOffVideoFavorite(video)
            }

            state.value.image?.let { image ->
                onOffImageFavorite(image)
            }
        }
    }

    private suspend fun onOffVideoFavorite(video: VideoSearch) {
        if (isFavoriteVideoUseCase(video.id)) {
            removeFavoriteVideoUseCase(video.id)
            setFavoriteStatus(false)
        } else {
            addFavoriteVideoUseCase(video)
            setFavoriteStatus(true)
        }
    }

    private suspend fun onOffImageFavorite(image: ImageSearch) {
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