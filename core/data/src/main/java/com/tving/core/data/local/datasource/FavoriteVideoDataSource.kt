package com.tving.core.data.local.datasource

import com.tving.core.domain.model.pixabay.VideoSearch
import kotlinx.coroutines.flow.Flow

interface FavoriteVideoDataSource {
    fun getFavoriteVideos(): Flow<List<VideoSearch>>
    suspend fun addFavoriteVideo(video: VideoSearch)
    suspend fun removeFavoriteVideo(videoId: Int)
    suspend fun isFavoriteVideo(videoId: Int): Boolean
}