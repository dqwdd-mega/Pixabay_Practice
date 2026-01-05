package com.pixabay.feat.favorites.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.pixabay.core.navigation.NavigationRoute
import com.pixabay.feat.favorites.FavoriteRoute

fun NavController.navigateToFavorite(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.FavoriteScreen.route, navOptions)

fun NavGraphBuilder.favoriteNavigation() {
    composable(
        route = NavigationRoute.FavoriteScreen.route,
    ) {
        FavoriteRoute()
    }
}
