package com.tving.feat.contentdetail

import com.tving.core.common.base.BaseContract
import com.tving.feat.contentdetail.model.ContentInfo

object ContentDetailContract {

    data class ContentDetailState(
        val loading: Boolean = false,
        val contentInfo: ContentInfo = ContentInfo(),
        val isFavorite: Boolean = false,
    ) : BaseContract.UiState

    sealed interface Event : BaseContract.Event

    sealed interface SideEffect : BaseContract.SideEffect
}