package com.tving.core.navigation

sealed class NavigationRoute(val route: String) {
    data object HomeScreen : NavigationRoute("home")
    data object FavoriteScreen : NavigationRoute("favorite")
    data object ContentDetailScreen : NavigationRoute("contentdetail/{contentType}/{contentId}") {
        fun createRoute(contentType: String, contentId: Int): String {
            return "contentdetail/$contentType/$contentId"
        }
        
        const val CONTENT_TYPE_VIDEO = "video"
        const val CONTENT_TYPE_IMAGE = "image"
    }
}