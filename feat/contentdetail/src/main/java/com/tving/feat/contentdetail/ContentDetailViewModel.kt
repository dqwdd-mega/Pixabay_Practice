package com.tving.feat.contentdetail

import androidx.lifecycle.viewModelScope
import com.tving.core.common.base.BaseViewModel
import com.tving.core.domain.usecase.AddFavoriteImageUseCase
import com.tving.core.domain.usecase.AddFavoriteVideoUseCase
import com.tving.core.domain.usecase.GetFavoriteImagesUseCase
import com.tving.core.domain.usecase.GetFavoriteVideosUseCase
import com.tving.core.domain.usecase.IsFavoriteImageUseCase
import com.tving.core.domain.usecase.IsFavoriteVideoUseCase
import com.tving.core.domain.usecase.RemoveFavoriteImageUseCase
import com.tving.core.domain.usecase.RemoveFavoriteVideoUseCase
import com.tving.core.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    private val getFavoriteVideosUseCase: GetFavoriteVideosUseCase,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val isFavoriteVideoUseCase: IsFavoriteVideoUseCase,
    private val isFavoriteImageUseCase: IsFavoriteImageUseCase,
    private val addFavoriteVideoUseCase: AddFavoriteVideoUseCase,
    private val addFavoriteImageUseCase: AddFavoriteImageUseCase,
    private val removeFavoriteVideoUseCase: RemoveFavoriteVideoUseCase,
    private val removeFavoriteImageUseCase: RemoveFavoriteImageUseCase,
) : BaseViewModel<ContentDetailContract.ContentDetailState, ContentDetailContract.Event, ContentDetailContract.SideEffect>() {

    override val _state = MutableStateFlow(ContentDetailContract.ContentDetailState())

    override suspend fun handleEvent(event: ContentDetailContract.Event) {
    }
    
    fun loadContent(contentType: String, contentId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            
            when (contentType) {
                NavigationRoute.ContentDetailScreen.CONTENT_TYPE_VIDEO -> {
                    val videos = getFavoriteVideosUseCase().firstOrNull() ?: emptyList()
                    val video = videos.find { it.id == contentId }
                    val isFavorite = isFavoriteVideoUseCase(contentId)
                    _state.value = _state.value.copy(
                        loading = false,
                        video = video,
                        image = null,
                        isFavorite = isFavorite
                    )
                }
                NavigationRoute.ContentDetailScreen.CONTENT_TYPE_IMAGE -> {
                    val images = getFavoriteImagesUseCase().firstOrNull() ?: emptyList()
                    val image = images.find { it.id == contentId }
                    val isFavorite = isFavoriteImageUseCase(contentId)
                    _state.value = _state.value.copy(
                        loading = false,
                        image = image,
                        video = null,
                        isFavorite = isFavorite
                    )
                }
                else -> {
                    _state.value = _state.value.copy(loading = false)
                }
            }
        }
    }
    
    fun onOffFavorite() {
        viewModelScope.launch {
            val currentState = _state.value
            when {
                currentState.video != null -> {
                    if (isFavoriteVideoUseCase(currentState.video.id)) {
                        removeFavoriteVideoUseCase(currentState.video.id)
                        _state.value = _state.value.copy(isFavorite = false)
                    } else {
                        addFavoriteVideoUseCase(currentState.video)
                        _state.value = _state.value.copy(isFavorite = true)
                    }
                }
                currentState.image != null -> {
                    if (isFavoriteImageUseCase(currentState.image.id)) {
                        removeFavoriteImageUseCase(currentState.image.id)
                        _state.value = _state.value.copy(isFavorite = false)
                    } else {
                        addFavoriteImageUseCase(currentState.image)
                        _state.value = _state.value.copy(isFavorite = true)
                    }
                }
            }
        }
    }
}