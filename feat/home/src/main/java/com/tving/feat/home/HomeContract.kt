package com.tving.feat.home

import com.tving.core.common.base.BaseContract

object HomeContract {

    data class HomeState(
        val loading: Boolean = false,
        val searchText: String = "",
        val showSearchRightContent: Boolean = false,
    ) : BaseContract.UiState

    sealed interface Event : BaseContract.Event {
        data object ClickSearch : Event
    }

    sealed interface SideEffect : BaseContract.SideEffect {
        data object NavigateToContentDetail : SideEffect
    }
}