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
     * @param page 페이지 번호
     * @param perPage 페이지당 결과 수
     */
    suspend fun searchImage(
        query: String,
        page: Int = 1,
        perPage: Int = 20
    ): BaseResult<List<ImageSearch>>
}