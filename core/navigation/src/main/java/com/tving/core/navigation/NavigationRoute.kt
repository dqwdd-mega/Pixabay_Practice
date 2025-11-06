package com.tving.core.navigation

sealed class NavigationRoute(val route: String) {
    data object HomeScreen : NavigationRoute("home")
    data object FavoriteScreen : NavigationRoute("favorite")
    data object ContentDetailScreen : NavigationRoute("contentdetail")
}