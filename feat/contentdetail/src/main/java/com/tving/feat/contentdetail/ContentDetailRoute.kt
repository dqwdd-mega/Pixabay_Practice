package com.tving.feat.contentdetail

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.feat.contentdetail.component.ContentInfoCard
import com.tving.feat.contentdetail.component.MediaComponent
import com.tving.feat.contentdetail.component.UserInfoCard
import com.tving.feat.contentdetail.model.ContentInfo

@Composable
fun ContentDetailRoute(
    video: VideoSearch? = null,
    image: ImageSearch? = null,
    viewModel: ContentDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(video, image) {
        viewModel.loadContent(video, image)
    }
    
    ContentDetailScreen(
        loading = state.loading,
        contentInfo = state.contentInfo,
        isFavorite = state.isFavorite,
        onClickFavorite = { viewModel.onOffFavorite() }
    )
}

@Composable
fun ContentDetailScreen(
    loading: Boolean,
    contentInfo: ContentInfo,
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
            MediaComponent(
                videoUrl = contentInfo.videoUrl,
                thumbnailUrl = contentInfo.thumbnailUrl,
                imageUrl = contentInfo.imageUrl
            )

            Spacer(modifier = Modifier.height(20.dp))

            UserInfoCard(
                userName = contentInfo.userName,
                userImageUrl = contentInfo.userImageUrl,
                isFavorite = isFavorite,
                onClickFavorite = onClickFavorite
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContentInfoCard(
                stats = contentInfo.getStats(),
                tags = contentInfo.tags
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
        contentInfo = ContentInfo(),
        isFavorite = false,
        onClickFavorite = {}
    )
}