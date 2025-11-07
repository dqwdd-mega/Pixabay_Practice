package com.tving.core.domain.usecase

import com.tving.core.domain.model.BaseResult
import com.tving.core.domain.model.video.VideoSearch
import com.tving.core.domain.repository.PixabayRepository
import javax.inject.Inject

class GetSearchVideoUseCase @Inject constructor(
    private val repository: PixabayRepository
) {
    suspend operator fun invoke(query: String): BaseResult<List<VideoSearch>> {
        return repository.searchVideo(query)
    }
}