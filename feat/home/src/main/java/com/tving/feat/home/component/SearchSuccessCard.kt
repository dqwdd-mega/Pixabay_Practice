package com.tving.feat.home.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.GreyD9D9D9
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.feat.home.R

@Composable
fun SearchSuccessCard(
    modifier: Modifier = Modifier,
    images: List<com.tving.core.domain.model.pixabay.ImageSearch>,
    totalImageHits: Int,
    searchImagePagingLoading: Boolean,
    firstVideo: com.tving.core.domain.model.pixabay.VideoSearch?,
    onRequestMore: () -> Unit = {},
) {
    val configuration = LocalConfiguration.current
    val isLand = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val gridColumns = if (isLand) 3 else 2
    val gridState = rememberLazyGridState()

    val requestMore by remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = gridState.layoutInfo.totalItemsCount
            val hasMoreImages = images.size < totalImageHits
            lastVisibleItem != null && lastVisibleItem.index >= totalItems - 3 && !searchImagePagingLoading && hasMoreImages
        }
    }

    LaunchedEffect(requestMore) {
        if (requestMore && images.isNotEmpty()) {
            onRequestMore()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridColumns),
        modifier = modifier
            .background(color = White)
            .fillMaxSize(),
        state = gridState,
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
                    text = stringResource(R.string.text_search_results),
                    color = Black,
                    fontSize = 24.sp
                )
                Text(
                    text = stringResource(R.string.text_image_by_pixabay),
                    color = Black
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        // video
        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }) {
            FeaturedVideoSection(firstVideo = firstVideo)
        }

        // image
        items(images) { image ->
            SearchImageItem(image = image)
        }

        if (searchImagePagingLoading) {
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(gridColumns) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun FeaturedVideoSection(
    modifier: Modifier = Modifier,
    firstVideo: com.tving.core.domain.model.pixabay.VideoSearch?
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.text_featured_video),
            color = Black,
        )
        Spacer(modifier = Modifier.height(10.dp))

        firstVideo?.let { video ->
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

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewFeaturedVideoSection() {
    FeaturedVideoSection(firstVideo = null)
}

@Composable
fun SearchImageItem(
    modifier: Modifier = Modifier,
    image: com.tving.core.domain.model.pixabay.ImageSearch
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(color = GreyD9D9D9)
            .border(
                width = 1.dp,
                color = Black,
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        AsyncImage(
            model = image.previewURL,
            contentDescription = image.tags,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
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
        images = emptyList(),
        totalImageHits = 0,
        searchImagePagingLoading = false,
        firstVideo = null
    )
}