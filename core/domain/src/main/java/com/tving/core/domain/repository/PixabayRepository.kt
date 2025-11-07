package com.tving.core.domain.repository

import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.image.ImageSearch
import com.tving.core.domain.model.video.VideoSearch

interface PixabayRepository {
    /**
     * 비디오 검색
     * @param query 검색 쿼리
     */
    suspend fun searchVideo(query: String): BaseResult<List<VideoSearch>>

    /**
     * 이미지 검색
     * @param query 검색 쿼리
     * @param imageType 이미지 타입 (all, photo, illustration, vector)
     */
    suspend fun searchImage(query: String, imageType: String): BaseResult<List<ImageSearch>>
}