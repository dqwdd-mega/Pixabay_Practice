package com.tving.feat.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.HomeScreen.route, navOptions)

fun NavGraphBuilder.homeNavigation() {
    composable(
        route = NavigationRoute.HomeScreen.route,
    ) {
        HomeRoute()
    }
}
