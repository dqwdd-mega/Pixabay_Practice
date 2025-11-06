package com.example.tving_task_2025_11_05

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.tving_task_2025_11_05.navigation.BottomNaviDestination
import com.example.tving_task_2025_11_05.navigation.MainNavHost
import com.tving.core.designsystem.BottomNaviItems
import com.tving.core.designsystem.TvingBottomNavigation
import com.tving.core.designsystem.theme.ColorTokens.GreyD9D9D9
import com.tving.core.navigation.NavigationRoute

@Composable
fun TvingApp(
    appState: TvingAppState = rememberOKetAppState()
) {
    val startDestination = NavigationRoute.HomeScreen.route

    Scaffold(
        bottomBar = {
            if (appState.isBottomBarVisible()) {
                TvingBottomBar(
                    destinations = appState.bottomBarDestination,
                    onNavigateToDestination = appState::navigateToBottomNaviDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                MainNavHost(
                    appState = appState,
                    startDestination = startDestination
                )
            }
        }
    )
}

@Composable
private fun TvingBottomBar(
    modifier: Modifier = Modifier,
    destinations: List<BottomNaviDestination>,
    onNavigateToDestination: (BottomNaviDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    TvingBottomNavigation {
        destinations.forEach { destination ->
            val isSelected = currentDestination.isSelectedBottomNaviPage(destination)
            BottomNaviItems(
                modifier = modifier.background(color = GreyD9D9D9),
                selected = isSelected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = if (isSelected) {
                            painterResource(id = destination.selectedIcon)
                        } else {
                            painterResource(id = destination.unselectedIcon)
                        },
                        contentDescription = null,
                    )
                },
                label = destination.routeName
            )
        }
    }
}

private fun NavDestination?.isSelectedBottomNaviPage(destination: BottomNaviDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
