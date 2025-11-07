package com.tving.feat.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tving.core.designsystem.theme.ColorTokens.White
import com.tving.core.navigation.LocalNavController
import com.tving.core.navigation.NavigationRoute
import com.tving.feat.contentdetail.navigation.navigateToContentDetail
import com.tving.feat.home.component.HomeSearchBar
import com.tving.feat.home.component.SearchEmptyCard
import com.tving.feat.home.component.SearchFailCard
import com.tving.feat.home.component.SearchIdleCard
import com.tving.feat.home.component.SearchSuccessCard
import com.tving.feat.home.model.SearchState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is HomeContract.SideEffect.NavigateToContentDetailWithVideo -> {
                    navController.navigateToContentDetail(
                        contentType = NavigationRoute.ContentDetailScreen.CONTENT_TYPE_VIDEO,
                        contentId = sideEffect.video.id
                    )
                }
                is HomeContract.SideEffect.NavigateToContentDetailWithImage -> {
                    navController.navigateToContentDetail(
                        contentType = NavigationRoute.ContentDetailScreen.CONTENT_TYPE_IMAGE,
                        contentId = sideEffect.image.id
                    )
                }
            }
        }
    }

    HomeScreen(
        state = state,
        onChangeSearchText = { viewModel.updateSearchText(it) },
        onClickSearchTextClear = { viewModel.updateSearchText("") },
        searchContent = { viewModel.searchContent() },
        onLoadMoreImages = { viewModel.searchImages() },
        onClickOnOffVideoFavorite = { viewModel.onOffVideoFavorite() },
        onClickOnOffImageFavorite = { image -> viewModel.onOffImageFavorite(image) },
        onClickVideoContent = { video -> viewModel.intent(HomeContract.Event.ClickVideoContent(video)) },
        onClickImageContent = { image -> viewModel.intent(HomeContract.Event.ClickImageContent(image)) },
    )
}

@Composable
fun HomeScreen(
    state: HomeContract.HomeState,
    onChangeSearchText: (String) -> Unit,
    onClickSearchTextClear: () -> Unit,
    searchContent: () -> Unit,
    onLoadMoreImages: () -> Unit = {},
    onClickOnOffVideoFavorite: () -> Unit = {},
    onClickOnOffImageFavorite: (com.tving.core.domain.model.pixabay.ImageSearch) -> Unit = {},
    onClickVideoContent: (com.tving.core.domain.model.pixabay.VideoSearch) -> Unit = {},
    onClickImageContent: (com.tving.core.domain.model.pixabay.ImageSearch) -> Unit = {},
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
            HomeSearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchText,
                onValueChange = onChangeSearchText,
                onClickClear = onClickSearchTextClear,
                onSearch = searchContent,
                showRightContent = state.showSearchRightContent,
                placeholderText = stringResource(com.tving.feat.home.R.string.text_for_search_placeholder),
            )

            Spacer(modifier = Modifier.height(30.dp))

            when (state.searchState) {
                SearchState.Idle -> SearchIdleCard(modifier = Modifier)
                SearchState.Success -> SearchSuccessCard(
                    modifier = Modifier,
                    images = state.images,
                    totalImageHits = state.totalImageHits,
                    searchImagePagingLoading = state.searchImagePagingLoading,
                    firstVideo = state.firstVideo,
                    videoFavorite = state.isFirstVideoFavorite,
                    onRequestMore = onLoadMoreImages,
                    onClickOnOffVideoFavorite = onClickOnOffVideoFavorite,
                    isImageFavorite = { imageId -> state.isImageFavorite(imageId) },
                    onClickOnOffImageFavorite = onClickOnOffImageFavorite,
                    onClickVideoContent = onClickVideoContent,
                    onClickImageContent = onClickImageContent,
                )
                SearchState.Empty -> SearchEmptyCard(modifier = Modifier)
                SearchState.Fail -> SearchFailCard(modifier = Modifier)
            }
        }

        if (state.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview
fun PreviewHomeScreen() {
    HomeScreen(
        state = HomeContract.HomeState(),
        onChangeSearchText = {},
        onClickSearchTextClear = {},
        searchContent = {},
        onLoadMoreImages = {},
        onClickOnOffVideoFavorite = {},
    )
}