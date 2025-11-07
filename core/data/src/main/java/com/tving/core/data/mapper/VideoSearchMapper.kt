package com.tving.core.data.mapper

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.pixabay.VideoInfo
import com.tving.core.data.model.pixabay.VideoQuality
import com.tving.core.data.model.pixabay.VideoSearchResponse
import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.pixabay.VideoInfoDomain
import com.tving.core.domain.model.pixabay.VideoQualityDomain
import com.tving.core.domain.model.pixabay.VideoSearch

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