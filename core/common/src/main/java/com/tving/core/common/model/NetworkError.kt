package com.tving.core.common.model

sealed class NetworkError {
    data class RateLimitExceeded(
        val message: String = "API rate limit exceeded. Please try again later."
    ) : NetworkError()
    
    data class Unauthorized(
        val message: String = "Authentication failed."
    ) : NetworkError()
    
    data class Forbidden(
        val message: String = "Access forbidden."
    ) : NetworkError()
    
    data class NotFound(
        val message: String = "Resource not found."
    ) : NetworkError()
    
    data class BadRequest(
        val message: String = "Invalid request."
    ) : NetworkError()
    
    data class ServerError(
        val code: Int,
        val message: String = "Server error occurred."
    ) : NetworkError()
    
    data class NetworkTimeout(
        val message: String = "Network timeout. Please check your connection."
    ) : NetworkError()
    
    data class NetworkUnavailable(
        val message: String = "No internet connection."
    ) : NetworkError()
    
    data class Unknown(
        val throwable: Throwable,
        val message: String = "An unexpected error occurred."
    ) : NetworkError()
}