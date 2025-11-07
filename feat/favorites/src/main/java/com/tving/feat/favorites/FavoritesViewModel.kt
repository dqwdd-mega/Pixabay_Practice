package com.tving.feat.favorites

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tving.core.common.base.BaseViewModel
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.usecase.AddFavoriteImageUseCase
import com.tving.core.domain.usecase.AddFavoriteVideoUseCase
import com.tving.core.domain.usecase.GetFavoriteImagesUseCase
import com.tving.core.domain.usecase.GetFavoriteVideosUseCase
import com.tving.core.domain.usecase.IsFavoriteImageUseCase
import com.tving.core.domain.usecase.IsFavoriteVideoUseCase
import com.tving.core.domain.usecase.RemoveFavoriteImageUseCase
import com.tving.core.domain.usecase.RemoveFavoriteVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteVideosUseCase: GetFavoriteVideosUseCase,
    private val isFavoriteVideoUseCase: IsFavoriteVideoUseCase,
    private val addFavoriteVideoUseCase: AddFavoriteVideoUseCase,
    private val removeFavoriteVideoUseCase: RemoveFavoriteVideoUseCase,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val isFavoriteImageUseCase: IsFavoriteImageUseCase,
    private val addFavoriteImageUseCase: AddFavoriteImageUseCase,
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
) : BaseViewModel<FavoritesContract.FavoritesState, FavoritesContract.Event, FavoritesContract.SideEffect>() {

    override val _state = MutableStateFlow(FavoritesContract.FavoritesState())

    init {
        getFavoriteVideos()
        getFavoriteImages()
    }

    override suspend fun handleEvent(event: FavoritesContract.Event) {
    }

    private fun getFavoriteVideos() {
        getFavoriteVideosUseCase()
            .onEach { favoriteVideos ->
                reduce { copy(favoriteVideos = favoriteVideos) }
                Log.e("tetest", "tetest, 111, favoriteVideos === $favoriteVideos")
            }
            .launchIn(viewModelScope)
    }

    private fun getFavoriteImages() {
        getFavoriteImagesUseCase()
            .onEach { favoriteImages ->
                reduce { copy(favoriteImages = favoriteImages) }
            }
            .launchIn(viewModelScope)
    }

    fun onOffVideoFavorite(video: VideoSearch) {
        viewModelScope.launch {
            if (isFavoriteVideoUseCase(video.id)) {
                removeFavoriteVideoUseCase(video.id)
            } else {
                addFavoriteVideoUseCase(video)
            }
        }
    }

    fun onOffImageFavorite(image: ImageSearch) {
        viewModelScope.launch {
            if (isFavoriteImageUseCase(image.id)) {
                removeFavoriteImageUseCase(image.id)
            } else {
                addFavoriteImageUseCase(image)
            }
        }
    }
}