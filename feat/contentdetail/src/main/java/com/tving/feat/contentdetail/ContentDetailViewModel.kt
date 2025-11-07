package com.tving.feat.contentdetail

import com.tving.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
) : BaseViewModel<ContentDetailContract.ContentDetailState, ContentDetailContract.Event, ContentDetailContract.SideEffect>() {

    override val _state = MutableStateFlow(ContentDetailContract.ContentDetailState())

    override suspend fun handleEvent(event: ContentDetailContract.Event) {
    }
}