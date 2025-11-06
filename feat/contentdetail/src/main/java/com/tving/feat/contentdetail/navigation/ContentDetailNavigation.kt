package com.tving.feat.contentdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.contentdetail.ContentDetailRoute

fun NavController.navigateToContentDetail(navOptions: NavOptions? = null) =
    navigate(NavigationRoute.ContentDetailScreen.route, navOptions)

fun NavGraphBuilder.contentDetailNavigation() {
    composable(
        route = NavigationRoute.ContentDetailScreen.route,
    ) {
        ContentDetailRoute()
    }
}
