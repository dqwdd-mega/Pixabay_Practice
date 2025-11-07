package com.tving.feat.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun VideoComponent(
    modifier: Modifier = Modifier,
    videoUrl: String,
    thumbnailUrl: String,
    favoriteOnOff: Boolean,
    onFavoriteClick: () -> Unit = {},
    onVideoClick: () -> Unit = {}
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
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Black,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                isPlaying = isPlaying.not()

                if (isPlaying) {
                    exoPlayer.play()
                } else {
                    exoPlayer.pause()
                }
            },
    ) {
        if (isPlaying.not()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "video thumbnail",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onVideoClick() },
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

            Image(
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 10.dp)
                    .align(Alignment.BottomEnd)
                    .clickable { onFavoriteClick() },
                painter = if (favoriteOnOff) {
                    painterResource(id = R.drawable.ic_heart)
                } else {
                    painterResource(id = R.drawable.ic_heart_empty)
                },
                contentDescription = "favorite",
            )
        } else {
            // 비디오 재생
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
@Preview
fun PreviewVideoThumbnailComponent() {
    VideoComponent(
        videoUrl = "https://...mp4",
        thumbnailUrl = "https://...jpg",
        favoriteOnOff = true
    )
}