package com.tving.feat.favorites

import com.tving.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
) : BaseViewModel<FavoritesContract.FavoritesState, FavoritesContract.Event, FavoritesContract.SideEffect>() {

    override val _state = MutableStateFlow(FavoritesContract.FavoritesState())

    override suspend fun handleEvent(event: FavoritesContract.Event) {
    }
}