package com.pixabay.core.domain.model

data class BaseResult<T>(
    val total: Int,
    val totalHits: Int,
    val data: T
)