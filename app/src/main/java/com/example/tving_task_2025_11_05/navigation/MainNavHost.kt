package com.example.tving_task_2025_11_05.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.tving_task_2025_11_05.TvingAppState
import com.tving.core.navigation.LocalNavController
import com.tving.feat.contentdetail.navigation.contentDetailNavigation
import com.tving.feat.favorites.navigation.favoriteNavigation
import com.tving.feat.home.navigation.homeNavigation

@Composable
fun MainNavHost(
    appState: TvingAppState,
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