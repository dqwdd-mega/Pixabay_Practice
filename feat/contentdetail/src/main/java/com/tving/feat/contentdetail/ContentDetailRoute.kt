package com.tving.feat.contentdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.tving.core.designsystem.theme.ColorTokens.White

@Composable
fun ContentDetailRoute(
    contentType: String,
    contentId: Int,
    viewModel: ContentDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(contentType, contentId) {
        viewModel.loadContent(contentType, contentId)
    }

    ContentDetailScreen(
        state = state,
        onClickFavorite = { viewModel.onOffFavorite() }
    )
}

@Composable
fun ContentDetailScreen(
    state: ContentDetailContract.ContentDetailState,
    onClickFavorite: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            AsyncImage(
                model = state.video?.thumbnailUrl ?: state.image?.previewURL,
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                modifier = Modifier
                    .clickable { onClickFavorite() },
                painter = if (state.isFavorite) {
                    painterResource(id = com.tving.core.designsystem.R.drawable.ic_heart)
                } else {
                    painterResource(id = com.tving.core.designsystem.R.drawable.ic_heart_empty)
                },
                contentDescription = "favorite",
            )
        }

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