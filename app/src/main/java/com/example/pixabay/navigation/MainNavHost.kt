package com.example.pixabay.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.pixabay.PixabayAppState
import com.pixabay.core.navigation.LocalNavController
import com.pixabay.feat.contentdetail.navigation.contentDetailNavigation
import com.pixabay.feat.favorites.navigation.favoriteNavigation
import com.pixabay.feat.home.navigation.homeNavigation

@Composable
fun MainNavHost(
    appState: PixabayAppState,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    CompositionLocalProvider(LocalNavController provides appState.navController) {
        NavHost(
            modifier = modifier,
            navController = appState.navController,
            startDestination = startDestination,
            exitTransition = { ExitTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            homeNavigation()
            favoriteNavigation()
            contentDetailNavigation()
        }
    }
}