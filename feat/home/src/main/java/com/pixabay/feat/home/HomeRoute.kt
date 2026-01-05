package com.pixabay.feat.home

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pixabay.core.designsystem.theme.ColorTokens.White
import com.pixabay.core.navigation.LocalNavController
import com.pixabay.feat.contentdetail.navigation.navigateToContentDetailWithImage
import com.pixabay.feat.contentdetail.navigation.navigateToContentDetailWithVideo
import com.pixabay.feat.home.component.HomeSearchBar
import com.pixabay.feat.home.component.SearchEmptyCard
import com.pixabay.feat.home.component.SearchFailCard
import com.pixabay.feat.home.component.SearchIdleCard
import com.pixabay.feat.home.component.SearchSuccessCard
import com.pixabay.feat.home.model.SearchState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavController.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 400L) {
            (context as Activity).finish()
        } else {
            backPressedState = true
            val finishText = context.getString(R.string.toast_noti_finish)
            Toast.makeText(context.applicationContext, finishText, Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is HomeContract.SideEffect.NavigateToContentDetailWithVideo -> {
                    navController.navigateToContentDetailWithVideo(
                        video = sideEffect.video
                    )
                }
                is HomeContract.SideEffect.NavigateToContentDetailWithImage -> {
                    navController.navigateToContentDetailWithImage(
                        image = sideEffect.image
                    )
                }

                is HomeContract.SideEffect.ShowToast -> TODO()
            }
        }
    }

    HomeScreen(
        loading = state.loading,
        searchText = state.searchText,
        showSearchRightContent = state.showSearchRightContent,
        searchState = state.searchState,
        images = state.images,
        totalImageHits = state.totalImageHits,
        searchImagePagingLoading = state.searchImagePagingLoading,
        firstVideo = state.firstVideo,
        isFirstVideoFavorite = state.isFirstVideoFavorite,
        isImageFavorite = { imageId -> state.isImageFavorite(imageId) },
        onChangeSearchText = { viewModel.updateSearchText(it) },
        onClickSearchTextClear = { viewModel.updateSearchText("") },
        searchContent = { viewModel.searchContent() },
        onLoadMoreImages = { viewModel.searchImages() },
        onClickOnOffVideoFavorite = { viewModel.onOffVideoFavorite() },
        onClickOnOffImageFavorite = { image -> viewModel.onOffImageFavorite(image) },
        onClickVideoContent = { video -> viewModel.intentThrottle(HomeContract.Event.ClickVideoContent(video)) },
        onClickImageContent = { image -> viewModel.intentThrottle(HomeContract.Event.ClickImageContent(image)) },
    )
}

@Composable
fun HomeScreen(
    loading: Boolean,
    searchText: String,
    showSearchRightContent: Boolean,
    searchState: SearchState,
    images: List<com.pixabay.core.domain.model.pixabay.ImageSearch>,
    totalImageHits: Int,
    searchImagePagingLoading: Boolean,
    firstVideo: com.pixabay.core.domain.model.pixabay.VideoSearch?,
    isFirstVideoFavorite: Boolean,
    isImageFavorite: (Int) -> Boolean,
    onChangeSearchText: (String) -> Unit,
    onClickSearchTextClear: () -> Unit,
    searchContent: () -> Unit,
    onLoadMoreImages: () -> Unit = {},
    onClickOnOffVideoFavorite: () -> Unit = {},
    onClickOnOffImageFavorite: (com.pixabay.core.domain.model.pixabay.ImageSearch) -> Unit = {},
    onClickVideoContent: (com.pixabay.core.domain.model.pixabay.VideoSearch) -> Unit = {},
    onClickImageContent: (com.pixabay.core.domain.model.pixabay.ImageSearch) -> Unit = {},
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
                value = searchText,
                onValueChange = onChangeSearchText,
                onClickClear = onClickSearchTextClear,
                onSearch = searchContent,
                showRightContent = showSearchRightContent,
                placeholderText = stringResource(R.string.text_for_search_placeholder),
            )

            Spacer(modifier = Modifier.height(30.dp))

            when (searchState) {
                SearchState.Idle -> SearchIdleCard(modifier = Modifier)
                SearchState.Success -> SearchSuccessCard(
                    modifier = Modifier,
                    images = images,
                    totalImageHits = totalImageHits,
                    searchImagePagingLoading = searchImagePagingLoading,
                    firstVideo = firstVideo,
                    videoFavorite = isFirstVideoFavorite,
                    onRequestMore = onLoadMoreImages,
                    onClickOnOffVideoFavorite = onClickOnOffVideoFavorite,
                    isImageFavorite = isImageFavorite,
                    onClickOnOffImageFavorite = onClickOnOffImageFavorite,
                    onClickVideoContent = onClickVideoContent,
                    onClickImageContent = onClickImageContent,
                )
                SearchState.Empty -> SearchEmptyCard(modifier = Modifier)
                SearchState.Fail -> SearchFailCard(modifier = Modifier)
            }
        }

        if (loading) {
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
        loading = false,
        searchText = "",
        showSearchRightContent = false,
        searchState = SearchState.Idle,
        images = emptyList(),
        totalImageHits = 0,
        searchImagePagingLoading = false,
        firstVideo = null,
        isFirstVideoFavorite = false,
        isImageFavorite = { false },
        onChangeSearchText = {},
        onClickSearchTextClear = {},
        searchContent = {},
        onLoadMoreImages = {},
        onClickOnOffVideoFavorite = {},
    )
}