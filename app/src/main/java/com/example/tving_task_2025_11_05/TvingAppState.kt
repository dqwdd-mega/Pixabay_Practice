package com.example.tving_task_2025_11_05

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.tving_task_2025_11_05.navigation.BottomNaviDestination
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.favorites.navigation.navigateToFavorite
import com.tving.feat.home.navigation.navigateToHome

@Composable
fun rememberOKetAppState(
    navController: NavHostController = rememberNavController(),
): TvingAppState {
    return remember(navController) {
        TvingAppState(
            navController = navController,
        )
    }
}

class TvingAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigateToBottomNaviDestination(bottomNaviDestination: BottomNaviDestination) {
        val bottomNaviOption = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (bottomNaviDestination) {
            BottomNaviDestination.HOME -> navController.navigateToHome(bottomNaviOption)
            BottomNaviDestination.FAVORITE -> navController.navigateToFavorite(bottomNaviOption)
        }
    }

    @Composable
    fun isBottomBarVisible(): Boolean {
        return when (currentDestination?.route) {
            NavigationRoute.HomeScreen.route -> true
            NavigationRoute.FavoriteScreen.route -> true
            else -> false
        }
    }

    val bottomBarDestination: List<BottomNaviDestination> = BottomNaviDestination.entries
}