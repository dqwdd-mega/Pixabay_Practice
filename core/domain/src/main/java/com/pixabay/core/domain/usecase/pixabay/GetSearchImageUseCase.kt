package com.pixabay.core.domain.usecase

import com.pixabay.core.domain.model.BaseResult
import com.pixabay.core.domain.model.pixabay.ImageSearch
import com.pixabay.core.domain.repository.PixabayRepository
import javax.inject.Inject

class GetSearchImageUseCase @Inject constructor(
    private val repository: PixabayRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        perPage: Int = 20
    ): BaseResult<List<ImageSearch>> {
        return repository.searchImage(query, page, perPage)
    }
}