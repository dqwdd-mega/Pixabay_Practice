package com.tving.feat.contentdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tving.core.designsystem.theme.ColorTokens.White

@Composable
fun ContentDetailRoute(
    viewModel: ContentDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    ContentDetailScreen(state = state)
}

@Composable
fun ContentDetailScreen(
    state: ContentDetailContract.ContentDetailState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(15.dp)
    ) {
        Text(text = "ContentDetail")

        if (state.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}