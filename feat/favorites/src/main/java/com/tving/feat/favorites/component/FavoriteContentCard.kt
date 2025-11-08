package com.tving.feat.favorites.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.GreyD9D9D9
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch

@Composable
fun FavoriteContentCard(
    modifier: Modifier = Modifier,
    videos: List<VideoSearch>,
    images: List<ImageSearch>,
    isVideoFavorite: (Int) -> Boolean = { false },
    isImageFavorite: (Int) -> Boolean = { false },
    onClickVideoContent: (VideoSearch) -> Unit = {},
    onClickImageContent: (ImageSearch) -> Unit = {},
    onClickOnOffVideoFavorite: (VideoSearch) -> Unit = {},
    onClickOnOffImageFavorite: (ImageSearch) -> Unit = {},
) {
    val configuration = LocalConfiguration.current
    val isLand = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val gridColumns = if (isLand) 3 else 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridColumns),
        modifier = modifier
            .background(color = White)
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item(
            span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }
        ) {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "즐겨찾기한 컨텐츠",
                    color = Black,
                    fontSize = 24.sp
                )
                Text(
                    text = "Image by Pixabay",
                    color = Black
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        // videos
        if (videos.isNotEmpty()) {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }) {
                Text(
                    text = "즐겨찾기한 비디오",
                    color = Black,
                )
            }

            items(videos) { video ->
                FavoriteItem(
                    path = video.thumbnailUrl,
                    tags = video.tags,
                    isFavorite = isVideoFavorite(video.id),
                    onClickContent = { onClickVideoContent(video) },
                    onClickFavorite = { onClickOnOffVideoFavorite(video) }
                )
            }

            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }) {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        // images
        if (images.isNotEmpty()) {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }) {
                Text(
                    text = "즐겨찾기한 이미지",
                    color = Black,
                )
            }

            items(images) { image ->
                FavoriteItem(
                    path = image.getPreviewImageUrl(),
                    tags = image.tags,
                    isFavorite = isImageFavorite(image.id),
                    onClickContent = { onClickImageContent(image) },
                    onClickFavorite = { onClickOnOffImageFavorite(image) }
                )
            }
        }

        if (videos.isEmpty() && images.isEmpty()) {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "즐겨찾기한 컨텐츠가 없습니다",
                        color = Black,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    path: String,
    tags: String,
    isFavorite: Boolean = false,
    onClickContent: () -> Unit = {},
    onClickFavorite: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickContent() }
            .aspectRatio(1f)
            .background(color = GreyD9D9D9)
            .border(
                width = 1.dp,
                color = Black
            ),
    ) {
        AsyncImage(
            model = path,
            contentDescription = tags,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Image(
            modifier = Modifier
                .padding(top = 10.dp, end = 10.dp)
                .align(Alignment.TopEnd)
                .clickable { onClickFavorite() },
            painter = if (isFavorite) {
                painterResource(id = com.tving.core.designsystem.R.drawable.ic_heart)
            } else {
                painterResource(id = com.tving.core.designsystem.R.drawable.ic_heart_empty)
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
fun PreviewFavoriteContentCard() {
    FavoriteContentCard(
        videos = emptyList(),
        images = emptyList(),
        isVideoFavorite = { false },
        isImageFavorite = { false },
        onClickVideoContent = {},
        onClickImageContent = {},
        onClickOnOffVideoFavorite = {},
        onClickOnOffImageFavorite = {}
    )
}