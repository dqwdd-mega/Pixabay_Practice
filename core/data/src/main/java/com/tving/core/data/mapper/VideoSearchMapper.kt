package com.tving.core.data.mapper

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.video.VideoSearchResponse
import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.video.VideoSearch

fun BaseResponse<List<VideoSearchResponse>>.toDomain(): BaseResult<List<VideoSearch>> {
    return BaseResult(
        total = total ?: 0,
        totalHits = totalHits ?: 0,
        data = hits?.map { it.toDomain() } ?: emptyList()
    )
}

fun VideoSearchResponse.toDomain(): VideoSearch {
    // 가장 적합한 품질 선택 (medium > small > tiny > large 순)
    val bestVideo = videos.medium ?: videos.small ?: videos.tiny ?: videos.large
    val videoUrl = bestVideo?.url ?: ""
    val thumbnailUrl = bestVideo?.thumbnail ?: ""

    return VideoSearch(
        id = id,
        pageURL = pageURL,
        type = type,
        tags = tags,
        duration = duration,
        pictureId = pictureId,
        videoUrl = videoUrl,
        thumbnailUrl = thumbnailUrl,
        views = views,
        downloads = downloads,
        likes = likes,
        comments = comments,
        userId = userId,
        user = user,
        userImageURL = userImageURL
    )
}