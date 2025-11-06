package com.example.tving_task_2025_11_05.navigation

import com.example.tving_task_2025_11_05.R

enum class BottomNaviDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val routeName: Int,
) {
    HOME(
        selectedIcon = R.drawable.ic_navi_home_selected,
        unselectedIcon = R.drawable.ic_navi_home_unselected,
        routeName = R.string.bottom_navigation_home,
    ),
    FAVORITE(
        selectedIcon = R.drawable.ic_navi_heart_selected,
        unselectedIcon = R.drawable.ic_navi_heart_unselected,
        routeName = R.string.bottom_navigation_favorite,
    ),
}