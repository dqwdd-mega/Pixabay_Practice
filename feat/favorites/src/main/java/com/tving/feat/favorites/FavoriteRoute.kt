package com.tving.feat.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
import com.tving.feat.favorites.component.FavoriteContentCard

@Composable
fun FavoriteRoute(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    FavoritesScreen(
        state = state,
        onClickOnOffVideoFavorite = { video -> viewModel.onOffVideoFavorite(video) },
        onClickOnOffImageFavorite = { image -> viewModel.onOffImageFavorite(image) },
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesContract.FavoritesState,
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
        onClickOnOffVideoFavorite = {},
        onClickOnOffImageFavorite = {},
    )
}