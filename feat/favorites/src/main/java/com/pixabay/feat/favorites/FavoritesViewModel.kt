package com.pixabay.feat.favorites

import androidx.lifecycle.viewModelScope
import com.pixabay.core.common.base.BaseViewModel
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.domain.usecase.AddFavoriteImageUseCase
import com.pixabay.core.domain.usecase.AddFavoriteVideoUseCase
import com.pixabay.core.domain.usecase.GetFavoriteImagesUseCase
import com.pixabay.core.domain.usecase.GetFavoriteVideosUseCase
import com.pixabay.core.domain.usecase.datastore.image.IsFavoriteImageUseCase
import com.pixabay.core.domain.usecase.IsFavoriteVideoUseCase
import com.pixabay.core.domain.usecase.RemoveFavoriteImageUseCase
import com.pixabay.core.domain.usecase.RemoveFavoriteVideoUseCase
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
        when (event) {
            is FavoritesContract.Event.ClickVideoContent -> {
                postSideEffect(
                    FavoritesContract.SideEffect.NavigateToContentDetailWithVideo(event.video)
                )
            }
            is FavoritesContract.Event.ClickImageContent -> {
                postSideEffect(
                    FavoritesContract.SideEffect.NavigateToContentDetailWithImage(event.image)
                )
            }
        }
    }

    private fun getFavoriteVideos() {
        getFavoriteVideosUseCase()
            .onEach { favoriteVideos ->
                reduce { copy(favoriteVideos = favoriteVideos) }
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
            runCatching {
                toggleVideoFavorite(video)
            }.onFailure { e ->
                handleError(e)
            }
        }
    }

    fun onOffImageFavorite(image: ImageSearch) {
        viewModelScope.launch {
            runCatching {
                toggleImageFavorite(image)
            }.onFailure { e ->
                handleError(e)
            }
        }
    }

    private suspend fun toggleVideoFavorite(video: VideoSearch) {
        if (isFavoriteVideoUseCase(video.id)) {
            removeFavoriteVideoUseCase(video.id)
        } else {
            addFavoriteVideoUseCase(video)
        }
    }

    private suspend fun toggleImageFavorite(image: ImageSearch) {
        if (isFavoriteImageUseCase(image.id)) {
            removeFavoriteImageUseCase(image.id)
        } else {
            addFavoriteImageUseCase(image)
        }
    }
}