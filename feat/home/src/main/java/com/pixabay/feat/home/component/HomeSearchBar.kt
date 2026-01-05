package com.pixabay.feat.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pixabay.core.designsystem.R
import com.pixabay.core.designsystem.component.PixabayInputTextField
import com.pixabay.core.designsystem.theme.ColorTokens.Black
import com.pixabay.core.designsystem.theme.ColorTokens.Black212121

@Composable
fun HomeSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClickClear: () -> Unit,
    onSearch: () -> Unit,
    showRightContent: Boolean,
    placeholderText: String,
) {
    val focusManager = LocalFocusManager.current

    PixabayInputTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholderText = placeholderText,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onSearch()
            }
        ),
        hasLeftContent = true,
        hasRightContent = showRightContent,
        leftContent = {
            Image(
                modifier = Modifier.padding(start = 10.dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search icon",
            )
        },
        rightContent = {
            Image(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        focusManager.clearFocus()
                        onClickClear()
                    },
                painter = painterResource(id = R.drawable.ic_close_circle),
                contentDescription = "clear button",
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(color = Black212121)
            )
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp,
                        vertical = 15.dp
                    )
                    .clickable { focusManager.clearFocus() },
                text = "Cancel",
                color = Black,
            )
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewHomeSearchBar() {
    HomeSearchBar(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange = {},
        onClickClear = {},
        onSearch = {},
        showRightContent = false,
        placeholderText = "Search videos and images...",
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewHomeSearchBarWithText() {
    HomeSearchBar(
        modifier = Modifier.fillMaxWidth(),
        value = "안녕하세요",
        onValueChange = {},
        onClickClear = {},
        onSearch = {},
        showRightContent = true,
        placeholderText = "Search videos and images...",
    )
}