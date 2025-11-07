package com.tving.feat.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tving.core.common.base.BaseViewModel
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import com.tving.core.domain.usecase.GetSearchImageUseCase
import com.tving.core.domain.usecase.GetSearchVideoUseCase
import com.tving.feat.home.model.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSearchVideoUseCase: GetSearchVideoUseCase,
    private val getSearchImageUseCase: GetSearchImageUseCase,
) : BaseViewModel<HomeContract.HomeState, HomeContract.Event, HomeContract.SideEffect>() {

    override val _state = MutableStateFlow(HomeContract.HomeState())

    override suspend fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.ClickSearch -> searchContent()
        }
    }

    /**
     * 통합 검색 (비디오 + 이미지)
     */
    fun searchContent() {
        val searchText = state.value.searchText
        if (searchText.isEmpty()) return

        viewModelScope.launch {
            try {
                updateLoading(true)

                val videoResult = getSearchVideoUseCase(query = searchText)
                val imageResult = getSearchImageUseCase(query = searchText)

                val hasResults = videoResult.data.isNotEmpty() || imageResult.data.isNotEmpty()

                if (hasResults) {
                    updateSearchState(SearchState.Success)
                    handleVideoResults(videoResult.data)
                    handleImageResults(imageResult.data)
                } else {
                    updateSearchState(SearchState.Empty)
                }
            } catch (e: Exception) {
                Log.e("tetest", "tetest, Exception === ${e.message}", e)
                updateSearchState(SearchState.Fail)
            } finally {
                updateLoading(false)
            }
        }
    }

    /**
     * 이미지 검색
     */
    fun searchImages(query: String) {
        if (query.isEmpty()) return

        viewModelScope.launch {
            try {
                updateLoading(true)

                val result = getSearchImageUseCase(query = query)

                if (result.data.isEmpty()) {
                    updateSearchState(SearchState.Empty)
                } else {
                    updateSearchState(SearchState.Success)
                    handleImageResults(result.data)
                }

            } catch (e: Exception) {
                updateSearchState(SearchState.Fail)
                e.printStackTrace()
            } finally {
                updateLoading(false)
            }
        }
    }

    /**
     * 비디오 결과 처리
     */
    private fun handleVideoResults(videos: List<VideoSearch>) {
        if (videos.isNotEmpty()) {
            reduce { copy(firstVideo = videos.first()) }
        }
    }

    /**
     * 이미지 결과 처리
     */
    private fun handleImageResults(images: List<ImageSearch>) {
    }

    private fun updateLoading(loading: Boolean) {
        reduce { copy(loading = loading) }
    }

    private fun updateSearchState(searchState: SearchState) {
        reduce { copy(searchState = searchState) }
    }

    fun updateSearchText(text: String) {
        reduce { copy(searchText = text) }

        if (text.isEmpty()) {
            updateShowSearchRightContent(false)
            updateSearchState(SearchState.Idle)
        } else {
            updateShowSearchRightContent(true)
        }
    }

    private fun updateShowSearchRightContent(show: Boolean) {
        reduce { copy(showSearchRightContent = show) }
    }
}