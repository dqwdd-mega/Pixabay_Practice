package com.tving.feat.home

import com.tving.core.common.base.BaseContract

object HomeContract {

    data class HomeState(
        val searchText: String = "",
    ) : BaseContract.UiState

    sealed interface Event : BaseContract.Event {
        data object ClickSearch : Event
    }

    sealed interface SideEffect : BaseContract.SideEffect {
        data object NavigateToContentDetail : SideEffect
    }
}