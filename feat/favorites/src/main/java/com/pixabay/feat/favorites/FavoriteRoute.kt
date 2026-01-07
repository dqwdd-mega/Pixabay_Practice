package com.pixabay.feat.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pixabay.core.designsystem.theme.ColorTokens.White
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.model.pixabay.VideoSearch
import com.pixabay.core.navigation.LocalNavController
import com.pixabay.feat.contentdetail.navigation.navigateToContentDetailWithImage
import com.pixabay.feat.contentdetail.navigation.navigateToContentDetailWithVideo
import com.pixabay.feat.favorites.component.FavoriteContentCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavoriteRoute(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is FavoritesContract.SideEffect.NavigateToContentDetailWithVideo -> {
                    navController.navigateToContentDetailWithVideo(
                        video = sideEffect.video
                    )
                }
                is FavoritesContract.SideEffect.NavigateToContentDetailWithImage -> {
                    navController.navigateToContentDetailWithImage(
                        image = sideEffect.image
                    )
                }

                is FavoritesContract.SideEffect.ShowToast -> TODO()
            }
        }
    }

    FavoritesScreen(
        loading = state.loading,
        videos = state.favoriteVideos,
        images = state.favoriteImages,
        isVideoFavorite = { videoId -> state.isVideoFavorite(videoId) },
        isImageFavorite = { imageId -> state.isImageFavorite(imageId) },
        onClickVideoContent = { video -> viewModel.intentThrottle(FavoritesContract.Event.ClickVideoContent(video)) },
        onClickImageContent = { image -> viewModel.intentThrottle(FavoritesContract.Event.ClickImageContent(image)) },
        onClickOnOffVideoFavorite = { video -> viewModel.onOffVideoFavorite(video) },
        onClickOnOffImageFavorite = { image -> viewModel.onOffImageFavorite(image) },
    )
}

@Composable
fun FavoritesScreen(
    loading: Boolean,
    videos: List<VideoSearch>,
    images: List<ImageSearch>,
    isVideoFavorite: (Int) -> Boolean,
    isImageFavorite: (Int) -> Boolean,
    onClickVideoContent: (VideoSearch) -> Unit = {},
    onClickImageContent: (ImageSearch) -> Unit = {},
    onClickOnOffVideoFavorite: (VideoSearch) -> Unit = {},
    onClickOnOffImageFavorite: (ImageSearch) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(15.dp)
    ) {
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            FavoriteContentCard(
                modifier = Modifier,
                videos = videos,
                images = images,
                isVideoFavorite = isVideoFavorite,
                isImageFavorite = isImageFavorite,
                onClickVideoContent = onClickVideoContent,
                onClickImageContent = onClickImageContent,
                onClickOnOffVideoFavorite = onClickOnOffVideoFavorite,
                onClickOnOffImageFavorite = onClickOnOffImageFavorite,
            )
        }
    }
}

@Composable
@Preview
fun PreviewFavoritesScreen() {
    FavoritesScreen(
        loading = false,
        videos = emptyList(),
        images = emptyList(),
        isVideoFavorite = { false },
        isImageFavorite = { false },
        onClickVideoContent = {},
        onClickImageContent = {},
        onClickOnOffVideoFavorite = {},
        onClickOnOffImageFavorite = {},
    )
}