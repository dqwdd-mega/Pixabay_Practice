package com.tving.feat.contentdetail

import com.tving.core.common.base.BaseContract
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.feat.contentdetail.model.ContentInfo

object ContentDetailContract {

    data class ContentDetailState(
        val loading: Boolean = false,
        val contentInfo: ContentInfo = ContentInfo(),
        val video: VideoSearch? = null,
        val image: ImageSearch? = null,
        val isFavorite: Boolean = false,
    ) : BaseContract.UiState

    sealed interface Event : BaseContract.Event

    sealed interface SideEffect : BaseContract.SideEffect
}