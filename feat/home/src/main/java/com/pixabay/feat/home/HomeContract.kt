package com.pixabay.feat.home

import com.pixabay.core.common.base.BaseContract
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.feat.home.model.SearchState

object HomeContract {

    data class HomeState(
        val loading: Boolean = false,
        val searchText: String = "",
        val showSearchRightContent: Boolean = false,
        val searchState: SearchState = SearchState.Idle,
        val firstVideo: VideoSearch? = null,
        val images: List<ImageSearch> = emptyList(),
        val currentImagePage: Int = 1,
        val searchImagePagingLoading: Boolean = false,
        val totalImageHits: Int = 0,
        val perPage: Int = 20,
        val favoriteVideos: List<VideoSearch> = emptyList(),
        val favoriteImages: List<ImageSearch> = emptyList(),
        val isFirstVideoFavorite: Boolean = false,
    ) : BaseContract.UiState {
        fun isImageFavorite(imageId: Int): Boolean {
            return favoriteImages.any { it.id == imageId }
        }
    }

    sealed interface Event : BaseContract.Event {
        data object ClickSearch : Event
        data object ClickOnOffVideoFavorite : Event
        data class ClickVideoContent(val video: VideoSearch) : Event
        data class ClickImageContent(val image: ImageSearch) : Event
    }

    sealed interface SideEffect : BaseContract.SideEffect {
        data class NavigateToContentDetailWithVideo(val video: VideoSearch) : SideEffect
        data class NavigateToContentDetailWithImage(val image: ImageSearch) : SideEffect
        data class ShowToast(val message: String) : SideEffect
    }
}