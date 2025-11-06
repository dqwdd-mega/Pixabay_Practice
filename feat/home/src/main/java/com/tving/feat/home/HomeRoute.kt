package com.tving.feat.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tving.core.navigation.LocalNavController

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current
}

@Composable
fun HomeScreen() {
    Text(text = "Home")
}

@Composable
@Preview
fun PreviewHomeScreen() {
    HomeScreen()
}