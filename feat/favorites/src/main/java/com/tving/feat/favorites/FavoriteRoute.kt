package com.tving.feat.favorites

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
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.navigation.LocalNavController
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.contentdetail.navigation.navigateToContentDetail
import com.tving.feat.favorites.component.FavoriteContentCard
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
                    navController.navigateToContentDetail(
                        contentType = NavigationRoute.ContentDetailScreen.CONTENT_TYPE_VIDEO,
                        contentId = sideEffect.video.id
                    )
                }
                is FavoritesContract.SideEffect.NavigateToContentDetailWithImage -> {
                    navController.navigateToContentDetail(
                        contentType = NavigationRoute.ContentDetailScreen.CONTENT_TYPE_IMAGE,
                        contentId = sideEffect.image.id
                    )
                }
            }
        }
    }

    FavoritesScreen(
        state = state,
        onClickVideoContent = { video -> viewModel.intent(FavoritesContract.Event.ClickVideoContent(video)) },
        onClickImageContent = { image -> viewModel.intent(FavoritesContract.Event.ClickImageContent(image)) },
        onClickOnOffVideoFavorite = { video -> viewModel.onOffVideoFavorite(video) },
        onClickOnOffImageFavorite = { image -> viewModel.onOffImageFavorite(image) },
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesContract.FavoritesState,
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
        FavoriteContentCard(
            modifier = Modifier,
            videos = state.favoriteVideos,
            images = state.favoriteImages,
            isVideoFavorite = { videoId -> state.isVideoFavorite(videoId) },
            isImageFavorite = { imageId -> state.isImageFavorite(imageId) },
            onClickVideoContent = onClickVideoContent,
            onClickImageContent = onClickImageContent,
            onClickOnOffVideoFavorite = onClickOnOffVideoFavorite,
            onClickOnOffImageFavorite = onClickOnOffImageFavorite,
        )

        if (state.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview
fun PreviewFavoritesScreen() {
    FavoritesScreen(
        state = FavoritesContract.FavoritesState(),
        onClickVideoContent = {},
        onClickImageContent = {},
        onClickOnOffVideoFavorite = {},
        onClickOnOffImageFavorite = {},
    )
}