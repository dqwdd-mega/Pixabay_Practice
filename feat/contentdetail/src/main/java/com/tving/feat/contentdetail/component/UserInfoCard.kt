package com.tving.feat.contentdetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tving.core.designsystem.R

@Composable
fun UserInfoCard(
    userName: String,
    userImageUrl: String,
    isFavorite: Boolean,
    onClickFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(userImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "user profile image",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            )

            Text(
                text = userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Image(
            modifier = Modifier
                .size(32.dp)
                .clickable { onClickFavorite() },
            painter = if (isFavorite) {
                painterResource(id = R.drawable.ic_heart)
            } else {
                painterResource(id = R.drawable.ic_heart_empty)
            },
            contentDescription = "favorite",
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewUserInfoCard() {
    UserInfoCard(
        userName = "SLPix",
        userImageUrl = "https://example.com/user.jpg",
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
fun PreviewUserInfoCardFavorite() {
    UserInfoCard(
        userName = "SLPix",
        userImageUrl = "https://example.com/user.jpg",
        isFavorite = true,
        onClickFavorite = {}
    )
}