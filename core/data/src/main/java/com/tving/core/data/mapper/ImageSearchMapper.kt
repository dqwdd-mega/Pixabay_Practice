package com.tving.core.data.mapper

import com.tving.core.data.model.BaseResponse
import com.tving.core.data.model.image.ImageSearchResponse
import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.image.ImageSearch

fun BaseResponse<List<ImageSearchResponse>>.toDomain(): BaseResult<List<ImageSearch>> {
    return BaseResult(
        total = total ?: 0,
        totalHits = totalHits ?: 0,
        data = hits?.map { it.toDomain() } ?: emptyList()
    )
}

fun ImageSearchResponse.toDomain(): ImageSearch {
    return ImageSearch(
        id = id,
        pageURL = pageURL,
        type = type,
        tags = tags,
        previewURL = previewURL,
        webformatURL = webformatURL,
        largeImageURL = largeImageURL,
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        views = views,
        downloads = downloads,
        likes = likes,
        comments = comments,
        userId = userId,
        user = user,
        userImageURL = userImageURL
    )
}