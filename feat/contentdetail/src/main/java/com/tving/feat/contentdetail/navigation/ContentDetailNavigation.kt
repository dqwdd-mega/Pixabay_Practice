package com.tving.feat.contentdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.contentdetail.ContentDetailRoute

fun NavController.navigateToContentDetail(
    contentType: String,
    contentId: Int,
    navOptions: NavOptions? = null
) {
    navigate(
        NavigationRoute.ContentDetailScreen.createRoute(contentType, contentId),
        navOptions
    )
}

fun NavGraphBuilder.contentDetailNavigation() {
    composable(
        route = NavigationRoute.ContentDetailScreen.route,
        arguments = listOf(
            navArgument("contentType") { 
                type = NavType.StringType 
            },
            navArgument("contentId") { 
                type = NavType.IntType 
            }
        )
    ) { backStackEntry ->
        val contentType = backStackEntry.arguments?.getString("contentType") ?: ""
        val contentId = backStackEntry.arguments?.getInt("contentId") ?: 0
        
        ContentDetailRoute(
            contentType = contentType,
            contentId = contentId
        )
    }
}