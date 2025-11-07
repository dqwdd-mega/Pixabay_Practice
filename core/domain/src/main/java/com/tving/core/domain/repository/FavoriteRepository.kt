package com.tving.core.domain.repository

import com.tving.core.domain.model.pixabay.VideoSearch
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteVideos(): Flow<List<VideoSearch>>
    suspend fun addFavoriteVideo(video: VideoSearch)
    suspend fun removeFavoriteVideo(videoId: Int)
    suspend fun isFavoriteVideo(videoId: Int): Boolean
}