package com.pixabay.core.data.mapper

import com.pixabay.core.data.model.BaseResponse
import com.pixabay.core.data.model.pixabay.VideoInfo
import com.pixabay.core.data.model.pixabay.VideoQuality
import com.pixabay.core.data.model.pixabay.VideoSearchResponse
import com.pixabay.core.domain.model.BaseResult
import com.pixabay.core.domain.model.pixabay.VideoInfoDomain
import com.pixabay.core.domain.model.pixabay.VideoQualityDomain
import com.pixabay.core.domain.model.pixabay.VideoSearch

fun BaseResponse<List<VideoSearchResponse>>.toDomain(): BaseResult<List<VideoSearch>> {
    return BaseResult(
        total = total ?: 0,
        totalHits = totalHits ?: 0,
        data = hits?.map { it.toDomain() } ?: emptyList()
    )
}

fun VideoSearchResponse.toDomain(): VideoSearch {
    return VideoSearch(
        id = id ?: 0,
        pageURL = pageURL ?: "",
        type = type ?: "",
        tags = tags ?: "",
        duration = duration ?: 0,
        pictureId = pictureId ?: "",
        videos = videos?.toDomain(),
        views = views ?: 0,
        downloads = downloads ?: 0,
        likes = likes ?: 0,
        comments = comments ?: 0,
        userId = userId ?: 0,
        user = user ?: "",
        userImageURL = userImageURL ?: ""
    )
}

fun VideoQuality.toDomain(): VideoQualityDomain {
    return VideoQualityDomain(
        large = large?.toDomain(),
        medium = medium?.toDomain(),
        small = small?.toDomain(),
        tiny = tiny?.toDomain()
    )
}

fun VideoInfo.toDomain(): VideoInfoDomain {
    return VideoInfoDomain(
        url = url,
        width = width,
        height = height,
        size = size,
        thumbnail = thumbnail
    )
}