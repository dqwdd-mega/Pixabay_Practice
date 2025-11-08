package com.tving.feat.contentdetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tving.core.designsystem.R
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.GreyD9D9D9
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch

@Composable
fun DetailMediaComponent(
    modifier: Modifier = Modifier,
    video: VideoSearch? = null,
    image: ImageSearch? = null
) {
    when {
        video != null -> {
            DetailVideoPlayer(
                modifier = modifier,
                videoUrl = video.videoUrl,
                thumbnailUrl = video.thumbnailUrl
            )
        }
        image != null -> {
            DetailImageViewer(
                modifier = modifier,
                imageUrl = image.largeImageURL
            )
        }
        else -> {
            EmptyMediaPlaceholder(modifier = modifier)
        }
    }
}

@Composable
fun DetailVideoPlayer(
    modifier: Modifier = Modifier,
    videoUrl: String,
    thumbnailUrl: String
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Black,
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        if (isPlaying.not()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "video thumbnail",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(id = R.drawable.ic_play_circle),
                contentDescription = "play button",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp)
                    .clickable {
                        isPlaying = true
                        exoPlayer.play()
                    }
            )
        } else {
            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = true
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun DetailImageViewer(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Black,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun EmptyMediaPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(8.dp))
            .background(color = GreyD9D9D9)
            .border(
                width = 1.dp,
                color = Black,
                shape = RoundedCornerShape(8.dp)
            )
    )
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewEmptyMediaPlaceholder() {
    EmptyMediaPlaceholder()
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewDetailVideoPlayer() {
    DetailVideoPlayer(
        videoUrl = "https://example.com/video.mp4",
        thumbnailUrl = "https://example.com/thumbnail.jpg"
    )
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewDetailVideoPlayerFavorited() {
    DetailVideoPlayer(
        videoUrl = "https://example.com/video.mp4",
        thumbnailUrl = "https://example.com/thumbnail.jpg"
    )
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewDetailImageViewer() {
    DetailImageViewer(
        imageUrl = "https://example.com/image.jpg"
    )
}

@Composable
@Preview(
    showBackground = true,
    apiLevel = 35,
    showSystemUi = false,
    backgroundColor = 0xFFFFFFFF
)
fun PreviewDetailImageViewerFavorited() {
    DetailImageViewer(
        imageUrl = "https://example.com/image.jpg"
    )
}