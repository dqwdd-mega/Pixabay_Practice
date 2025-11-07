package com.tving.feat.contentdetail

import com.tving.core.common.base.BaseContract

object ContentDetailContract {

    data class ContentDetailState(
        val loading: Boolean = false,
    ) : BaseContract.UiState

    sealed interface Event : BaseContract.Event

    sealed interface SideEffect : BaseContract.SideEffect
}