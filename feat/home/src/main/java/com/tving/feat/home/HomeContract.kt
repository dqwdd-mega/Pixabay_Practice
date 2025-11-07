package com.tving.feat.home

import com.tving.core.common.base.BaseContract
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.feat.home.model.SearchState

object HomeContract {

    data class HomeState(
        val loading: Boolean = false,
        val searchText: String = "",
        val showSearchRightContent: Boolean = false,
        val searchState: SearchState = SearchState.Idle,
        val firstVideo: VideoSearch? = null,
    ) : BaseContract.UiState

    sealed interface Event : BaseContract.Event {
        data object ClickSearch : Event
    }

    sealed interface SideEffect : BaseContract.SideEffect {
        data object NavigateToContentDetail : SideEffect
    }
}