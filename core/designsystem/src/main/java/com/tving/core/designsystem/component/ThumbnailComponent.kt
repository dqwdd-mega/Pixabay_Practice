package com.tving.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tving.core.designsystem.R
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.GreyD9D9D9

@Composable
fun ThumbnailComponent(
    modifier: Modifier = Modifier,
    showFavoriteState: Boolean = false,
    favoriteOnOff: Boolean,
    showBottomFavoriteState: Boolean = false
) {
    Box(
        modifier = modifier
            .size(width = 150.dp, height = 70.dp)
            .background(color = GreyD9D9D9)
            .border(
                width = 1.dp,
                color = Black,
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        if (showFavoriteState) {
            Image(
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
                    .align(Alignment.TopEnd),
                painter = if (favoriteOnOff) {
                    painterResource(id = R.drawable.ic_check_circle)
                } else {
                    painterResource(id = R.drawable.ic_heart_circle)
                },
                contentDescription = if (favoriteOnOff) {
                    "favorite on"
                } else {
                    "favorite off"
                },
            )
        }

        if (showBottomFavoriteState && favoriteOnOff) {
            Image(
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 10.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "favorite on",
            )
        }
    }
}

@Composable
@Preview
fun PreviewThumbnailComponentForVideo() {
    ThumbnailComponent(
        modifier = Modifier.size(width = 300.dp, height = 240.dp),
        showBottomFavoriteState = true,
        favoriteOnOff = true
    )
}

@Composable
@Preview
fun PreviewThumbnailComponent() {
    ThumbnailComponent(
        showFavoriteState = true,
        favoriteOnOff = false
    )
}