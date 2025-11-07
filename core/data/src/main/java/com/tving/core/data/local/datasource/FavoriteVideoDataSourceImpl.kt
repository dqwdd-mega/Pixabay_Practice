package com.tving.core.data.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tving.core.domain.model.pixabay.ImageSearch
import com.tving.core.domain.model.pixabay.VideoSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteVideoDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : FavoriteVideoDataSource {

    private val favoriteVideosKey = stringPreferencesKey(KEY_FAVORITE_VIDEOS)
    private val favoriteImagesKey = stringPreferencesKey(KEY_FAVORITE_IMAGES)

    override fun getFavoriteVideos(): Flow<List<VideoSearch>> {
        return dataStore.data.map { preferences ->
            val json = preferences[favoriteVideosKey] ?: return@map emptyList()
            try {
                val type = object : TypeToken<List<VideoSearch>>() {}.type
                gson.fromJson<List<VideoSearch>>(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun addFavoriteVideo(video: VideoSearch) {
        dataStore.edit { preferences ->
            val currentVideos = getCurrentVideos(preferences).toMutableList()
            currentVideos.add(video)
            preferences[favoriteVideosKey] = gson.toJson(currentVideos)
        }
    }

    override suspend fun removeFavoriteVideo(videoId: Int) {
        dataStore.edit { preferences ->
            val currentVideos = getCurrentVideos(preferences).toMutableList()
            currentVideos.removeAll { it.id == videoId }
            preferences[favoriteVideosKey] = gson.toJson(currentVideos)
        }
    }

    override suspend fun isFavoriteVideo(videoId: Int): Boolean {
        val videos = getCurrentVideos(dataStore.data.first())
        return videos.any { it.id == videoId }
    }

    private fun getCurrentVideos(preferences: Preferences): List<VideoSearch> {
        val json = preferences[favoriteVideosKey] ?: return emptyList()
        return try {
            val type = object : TypeToken<List<VideoSearch>>() {}.type
            gson.fromJson<List<VideoSearch>>(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getFavoriteImages(): Flow<List<ImageSearch>> {
        return dataStore.data.map { preferences ->
            val json = preferences[favoriteImagesKey] ?: return@map emptyList()
            try {
                val type = object : TypeToken<List<ImageSearch>>() {}.type
                gson.fromJson<List<ImageSearch>>(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun addFavoriteImage(image: ImageSearch) {
        dataStore.edit { preferences ->
            val currentImages = getCurrentImages(preferences).toMutableList()
            currentImages.removeAll { it.id == image.id }
            currentImages.add(image)
            preferences[favoriteImagesKey] = gson.toJson(currentImages)
        }
    }

    override suspend fun removeFavoriteImage(imageId: Int) {
        dataStore.edit { preferences ->
            val currentImages = getCurrentImages(preferences).toMutableList()
            currentImages.removeAll { it.id == imageId }
            preferences[favoriteImagesKey] = gson.toJson(currentImages)
        }
    }

    override suspend fun isFavoriteImage(imageId: Int): Boolean {
        val images = getCurrentImages(dataStore.data.first())
        return images.any { it.id == imageId }
    }

    private fun getCurrentImages(preferences: Preferences): List<ImageSearch> {
        val json = preferences[favoriteImagesKey] ?: return emptyList()
        return try {
            val type = object : TypeToken<List<ImageSearch>>() {}.type
            gson.fromJson<List<ImageSearch>>(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val KEY_FAVORITE_VIDEOS = "favorite_videos"
        private const val KEY_FAVORITE_IMAGES = "favorite_images"
    }
}