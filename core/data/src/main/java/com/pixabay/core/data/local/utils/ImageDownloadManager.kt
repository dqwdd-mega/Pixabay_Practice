package com.pixabay.core.data.local.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 이미지 다운로드하고 로컬 저장소에 저장하는 매니저
 * (Pixabay Hotlinking 정책 준수를 위해 즐겨찾기 이미지 로컬에 저장)
 */
@Singleton
class ImageDownloadManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val okHttpClient: OkHttpClient
) {
    
    companion object {
        private const val IMAGE_DIR = "favorite_images"
        private const val VIDEO_THUMBNAIL_DIR = "favorite_video_thumbnails"
    }
    
    /**
     * 비디오 썸네일
     * @param url 썸네일 URL
     * @param videoId 비디오 ID (파일명으로 사용)
     */
    suspend fun downloadVideoThumbnail(url: String, videoId: Int): String? {
        return downloadAndSave(url, VIDEO_THUMBNAIL_DIR, "video_${videoId}_thumbnail.jpg")
    }
    
    /**
     * 이미지 preview
     * @param url 이미지 URL
     * @param imageId 이미지 ID
     */
    suspend fun downloadImagePreview(url: String, imageId: Int): String? {
        return downloadAndSave(url, IMAGE_DIR, "image_${imageId}_preview.jpg")
    }
    
    /**
     * 실제 다운로드 및 저장 로직
     */
    private suspend fun downloadAndSave(
        url: String,
        directoryName: String,
        fileName: String
    ): String? = withContext(Dispatchers.IO) {
        try {
            val directory = File(context.filesDir, directoryName)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            if (file.exists()) {
                return@withContext file.absolutePath
            }

            val request = Request.Builder()
                .url(url)
                .build()
            
            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                return@withContext null
            }

            response.body?.byteStream()?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteVideoThumbnail(videoId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val directory = File(context.filesDir, VIDEO_THUMBNAIL_DIR)
            val file = File(directory, "video_${videoId}_thumbnail.jpg")
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteImageFiles(imageId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val directory = File(context.filesDir, IMAGE_DIR)
            val previewFile = File(directory, "image_${imageId}_preview.jpg")
            val webformatFile = File(directory, "image_${imageId}_webformat.jpg")
            val largeFile = File(directory, "image_${imageId}_large.jpg")
            
            var success = true
            if (previewFile.exists()) success = previewFile.delete() && success
            if (webformatFile.exists()) success = webformatFile.delete() && success
            if (largeFile.exists()) success = largeFile.delete() && success
            
            success
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}