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
import com.tving.core.common.util.formatNumber
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.feat.contentdetail.component.ContentInfoCard
import com.tving.feat.contentdetail.component.MediaComponent
import com.tving.feat.contentdetail.component.StatInfo
import com.tving.feat.contentdetail.component.UserInfoCard

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
            MediaComponent(
                video = video,
                image = image
            )

            Spacer(modifier = Modifier.height(20.dp))

            UserInfoCard(
                userName = video?.user ?: image?.user ?: "Unknown",
                userImageUrl = video?.userImageURL ?: image?.userImageURL ?: "",
                isFavorite = isFavorite,
                onClickFavorite = onClickFavorite
            )

            Spacer(modifier = Modifier.height(12.dp))

            ContentInfoCard(
                stats = listOf(
                    StatInfo(
                        label = "Type",
                        value = if (video != null) "Video" else "Photo"
                    ),
                    StatInfo(
                        label = "Views",
                        value = (video?.views ?: image?.views ?: 0).formatNumber()
                    ),
                    StatInfo(
                        label = "Likes",
                        value = (video?.likes ?: image?.likes ?: 0).formatNumber()
                    ),
                    StatInfo(
                        label = "Downloads",
                        value = (video?.downloads ?: image?.downloads ?: 0).formatNumber()
                    )
                ),
                tags = video?.tags ?: image?.tags ?: ""
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