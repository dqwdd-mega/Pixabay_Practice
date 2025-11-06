package com.tving.core.designsystem

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.GreyD9D9D9

@Composable
fun TvingBottomNavigation(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        content = content,
        containerColor = GreyD9D9D9,
    )
}

@Composable
fun RowScope.BottomNaviItems(
    selected: Boolean,
    label: Int,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = icon,
        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
        label = {
            Text(
                text = stringResource(label),
                color = Black,
            )
        },
        alwaysShowLabel = true,
    )
}