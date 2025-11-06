package com.tving.feat.home

import com.tving.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //
) : BaseViewModel<HomeContract.HomeState, HomeContract.Event, HomeContract.SideEffect>() {

    override val _state = MutableStateFlow(HomeContract.HomeState())

    override suspend fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.ClickSearch -> {
                // postSideEffect(event)
            }
        }
    }
}