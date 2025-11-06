package com.tving.feat.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.favorites.FavoriteRoute

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.FavoriteScreen.route, navOptions)

fun NavGraphBuilder.favoriteNavigation() {
    composable(
        route = NavigationRoute.FavoriteScreen.route,
    ) {
        FavoriteRoute()
    }
}
