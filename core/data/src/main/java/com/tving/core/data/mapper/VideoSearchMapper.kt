package com.tving.core.data.mapper

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.pixabay.VideoSearchResponse
import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.pixabay.VideoSearch

fun BaseResponse<List<VideoSearchResponse>>.toDomain(): BaseResult<List<VideoSearch>> {
    return BaseResult(
        total = total ?: 0,
        totalHits = totalHits ?: 0,
        data = hits?.map { it.toDomain() } ?: emptyList()
    )
}

fun VideoSearchResponse.toDomain(): VideoSearch {
    // 가장 적합한 품질 선택 (medium > small > tiny > large 순)
    val bestVideo = videos?.medium ?: videos?.small ?: videos?.tiny ?: videos?.large
    val videoUrl = bestVideo?.url ?: ""
    val thumbnailUrl = bestVideo?.thumbnail ?: ""

    return VideoSearch(
        id = id ?: 0,
        pageURL = pageURL ?: "",
        type = type ?: "",
        tags = tags ?: "",
        duration = duration ?: 0,
        pictureId = pictureId ?: "",
        videoUrl = videoUrl,
        thumbnailUrl = thumbnailUrl,
        views = views ?: 0,
        downloads = downloads ?: 0,
        likes = likes ?: 0,
        comments = comments ?: 0,
        userId = userId ?: 0,
        user = user ?: "",
        userImageURL = userImageURL ?: ""
    )
}