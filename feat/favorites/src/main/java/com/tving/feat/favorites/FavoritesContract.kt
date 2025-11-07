package com.tving.feat.favorites

import com.tving.core.common.base.BaseContract

object FavoritesContract {

    data class FavoritesState(
        val loading: Boolean = false,
    ) : BaseContract.UiState

    sealed interface Event : BaseContract.Event

    sealed interface SideEffect : BaseContract.SideEffect
}