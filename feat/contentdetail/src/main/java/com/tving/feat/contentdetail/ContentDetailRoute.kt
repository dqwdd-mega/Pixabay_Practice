package com.tving.feat.contentdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.feat.contentdetail.component.DetailMediaComponent

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
        loading = state.loading,
        video = state.video,
        image = state.image,
        isFavorite = state.isFavorite,
        onClickFavorite = { viewModel.onOffFavorite() }
    )
}

@Composable
fun ContentDetailScreen(
    loading: Boolean,
    video: com.tving.core.domain.model.pixabay.VideoSearch?,
    image: com.tving.core.domain.model.pixabay.ImageSearch?,
    isFavorite: Boolean,
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
            DetailMediaComponent(
                video = video,
                image = image
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                modifier = Modifier
                    .clickable { onClickFavorite() },
                painter = if (isFavorite) {
                    painterResource(id = com.tving.core.designsystem.R.drawable.ic_heart)
                } else {
                    painterResource(id = com.tving.core.designsystem.R.drawable.ic_heart_empty)
                },
                contentDescription = "favorite",
            )
        }

        if (loading) {
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
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewContentDetailScreenWithVideo() {
    ContentDetailScreen(
        loading = false,
        video = null,
        image = null,
        isFavorite = false,
        onClickFavorite = {}
    )
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewContentDetailScreenWithImage() {
    ContentDetailScreen(
        loading = false,
        video = null,
        image = null,
        isFavorite = true,
        onClickFavorite = {}
    )
}