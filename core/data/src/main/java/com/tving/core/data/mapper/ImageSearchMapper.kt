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
        id = id ?: 0,
        pageURL = pageURL ?: "",
        type = type ?: "",
        tags = tags ?: "",
        previewURL = previewURL ?: "",
        webformatURL = webformatURL ?: "",
        largeImageURL = largeImageURL ?: "",
        imageWidth = imageWidth ?: 0,
        imageHeight = imageHeight ?: 0,
        views = views ?: 0,
        downloads = downloads ?: 0,
        likes = likes ?: 0,
        comments = comments ?: 0,
        userId = userId ?: 0,
        user = user ?: "",
        userImageURL = userImageURL ?: ""
    )
}