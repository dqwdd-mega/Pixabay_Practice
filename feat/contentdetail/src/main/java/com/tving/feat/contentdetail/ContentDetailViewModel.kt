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
import com.tving.feat.contentdetail.model.ContentInfo
import com.tving.feat.contentdetail.model.ContentType
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
            setLoading(true)

            when (contentType) {
                NavigationRoute.ContentDetailScreen.CONTENT_TYPE_VIDEO -> {
                    val videos = getFavoriteVideosUseCase().firstOrNull() ?: emptyList()
                    val video = videos.find { it.id == contentId }
                    val isFavorite = isFavoriteVideoUseCase(contentId)

                    setContent(
                        contentInfo = ContentInfo.fromVideo(video),
                        isFavorite = isFavorite
                    )
                }

                NavigationRoute.ContentDetailScreen.CONTENT_TYPE_IMAGE -> {
                    val images = getFavoriteImagesUseCase().firstOrNull() ?: emptyList()
                    val image = images.find { it.id == contentId }
                    val isFavorite = isFavoriteImageUseCase(contentId)

                    setContent(
                        contentInfo = ContentInfo.fromImage(image),
                        isFavorite = isFavorite
                    )
                }

                else -> {
                    setLoading(false)
                }
            }
        }
    }

    fun onOffFavorite() {
        viewModelScope.launch {
            val currentState = _state.value
            val contentInfo = currentState.contentInfo

            when (contentInfo.type) {
                ContentType.VIDEO -> {
                    if (isFavoriteVideoUseCase(contentInfo.id)) {
                        removeFavoriteVideoUseCase(contentInfo.id)
                        setFavoriteStatus(false)
                    } else {
                        val videos = getFavoriteVideosUseCase().firstOrNull() ?: emptyList()
                        val video = videos.find { it.id == contentInfo.id }

                        video?.let {
                            addFavoriteVideoUseCase(it)
                            setFavoriteStatus(true)
                        }
                    }
                }

                ContentType.IMAGE -> {
                    if (isFavoriteImageUseCase(contentInfo.id)) {
                        removeFavoriteImageUseCase(contentInfo.id)
                        setFavoriteStatus(false)
                    } else {
                        val images = getFavoriteImagesUseCase().firstOrNull() ?: emptyList()
                        val image = images.find { it.id == contentInfo.id }

                        image?.let {
                            addFavoriteImageUseCase(it)
                            setFavoriteStatus(true)
                        }
                    }
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        reduce { copy(loading = loading) }
    }

    private fun setContent(contentInfo: ContentInfo, isFavorite: Boolean) {
        reduce {
            copy(
                loading = false,
                contentInfo = contentInfo,
                isFavorite = isFavorite
            )
        }
    }

    private fun setFavoriteStatus(isFavorite: Boolean) {
        reduce { copy(isFavorite = isFavorite) }
    }
}