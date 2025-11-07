package com.tving.feat.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tving.core.designsystem.R
import com.tving.core.designsystem.component.TvingInputTextField
import com.tving.core.designsystem.theme.ColorTokens.Black
import com.tving.core.designsystem.theme.ColorTokens.Black212121
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.feat.home.component.SearchIdleCard
import com.tving.feat.home.component.SearchSuccessCard
import com.tving.feat.home.model.SearchState

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
//    val navController = LocalNavController.current

    HomeScreen(
        state = state,
        onChangeSearchText = { viewModel.updateSearchText(it) },
    )
}

@Composable
fun HomeScreen(
    state: HomeContract.HomeState,
    onChangeSearchText: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TvingInputTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchText,
                onValueChange = onChangeSearchText,
                placeholderText = stringResource(com.tving.feat.home.R.string.text_for_search_placeholder),
                hasLeftContent = true,
                hasRightContent = state.showSearchRightContent,
                leftContent = {
                    Image(
                        modifier = Modifier.padding(start = 10.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "buttonClose",
                    )
                },
                rightContent = {
                    Image(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        painter = painterResource(id = R.drawable.ic_close_circle),
                        contentDescription = "buttonClose",
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
                            ),
                        text = "Cancel",
                        color = Black,
                    )
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            when (state.searchState) {
                SearchState.Idle -> SearchIdleCard(modifier = Modifier)
                SearchState.Success -> SearchSuccessCard(
                    modifier = Modifier,
                    state = state
                )
                SearchState.Empty -> SearchIdleCard(modifier = Modifier)
                SearchState.Fail -> SearchIdleCard(modifier = Modifier)
            }
        }
    }
}

@Composable
@Preview
fun PreviewHomeScreen() {
    HomeScreen(
        state = HomeContract.HomeState(),
        onChangeSearchText = {}
    )
}