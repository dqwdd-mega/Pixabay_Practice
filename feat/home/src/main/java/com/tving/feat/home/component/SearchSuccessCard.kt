package com.tving.feat.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tving.core.designsystem.component.ThumbnailComponent
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.GreyD9D9D9
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.feat.home.HomeContract
import com.tving.feat.home.R

@Composable
fun SearchSuccessCard(
    modifier: Modifier = Modifier,
    state: HomeContract.HomeState,
) {
    Column(
        modifier = modifier
            .background(color = White)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.text_search_results),
            color = Black,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.text_featured_video),
            color = Black,
        )

        state.firstVideo?.let { video ->
            VideoComponent(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                videoUrl = video.videoUrl,
                thumbnailUrl = video.thumbnailUrl,
                favoriteOnOff = true,
                showBottomFavoriteState = true
            )
        } ?: run {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(color = GreyD9D9D9)
                    .border(
                        width = 1.dp,
                        color = Black,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                Text(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    text = stringResource(R.string.text_video_is_gone),
                    color = Black,
                )
            }
        }

        ThumbnailComponent(
            favoriteOnOff = true,
            showBottomFavoriteState = true
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFF212121
)
fun PreviewSearchSuccessCard() {
    SearchSuccessCard(
        state = HomeContract.HomeState()
    )
}