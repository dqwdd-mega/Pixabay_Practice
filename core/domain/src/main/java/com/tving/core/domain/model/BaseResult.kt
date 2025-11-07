package com.tving.core.domain.model

data class BaseResult<T>(
    val total: Int,
    val totalHits: Int,
    val data: T
)