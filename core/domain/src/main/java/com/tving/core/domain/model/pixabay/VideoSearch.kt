package com.tving.core.domain.model.pixabay

data class VideoSearch(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val duration: Int,
    val pictureId: String,
    val videoUrl: String,
    val thumbnailUrl: String,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val userId: Int,
    val user: String,
    val userImageURL: String
)