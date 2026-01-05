package com.pixabay.feat.favorites

import com.pixabay.core.common.base.BaseContract
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch

object FavoritesContract {

    data class FavoritesState(
        val loading: Boolean = false,
        val favoriteVideos: List<VideoSearch> = emptyList(),
        val favoriteImages: List<ImageSearch> = emptyList(),
    ) : BaseContract.UiState {
        fun isVideoFavorite(videoId: Int): Boolean {
            return favoriteVideos.any { it.id == videoId }
        }
        
        fun isImageFavorite(imageId: Int): Boolean {
            return favoriteImages.any { it.id == imageId }
        }
    }

    sealed interface Event : BaseContract.Event {
        data class ClickVideoContent(val video: VideoSearch) : Event
        data class ClickImageContent(val image: ImageSearch) : Event
    }

    sealed interface SideEffect : BaseContract.SideEffect {
        data class NavigateToContentDetailWithVideo(val video: VideoSearch) : SideEffect
        data class NavigateToContentDetailWithImage(val image: ImageSearch) : SideEffect
        data class ShowToast(val message: String) : SideEffect
    }
}