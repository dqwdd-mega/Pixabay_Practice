package com.tving.core.common.exception

import com.tving.core.common.model.NetworkError

sealed class NetworkException(
    val error: NetworkError,
    message: String
) : Exception(message) {
    
    class RateLimitException(
        val retryAfter: Long? = null,
        message: String = "API rate limit exceeded"
    ) : NetworkException(NetworkError.RateLimitExceeded(message), message)
    
    class UnauthorizedException(
        message: String = "Unauthorized access"
    ) : NetworkException(NetworkError.Unauthorized(message), message)
    
    class ForbiddenException(
        message: String = "Access forbidden"
    ) : NetworkException(NetworkError.Forbidden(message), message)
    
    class NotFoundException(
        message: String = "Resource not found"
    ) : NetworkException(NetworkError.NotFound(message), message)
    
    class BadRequestException(
        message: String = "Bad request"
    ) : NetworkException(NetworkError.BadRequest(message), message)
    
    class ServerException(
        val code: Int,
        message: String = "Server error"
    ) : NetworkException(NetworkError.ServerError(code, message), message)
    
    class TimeoutException(
        message: String = "Request timeout"
    ) : NetworkException(NetworkError.NetworkTimeout(message), message)
    
    class NetworkUnavailableException(
        message: String = "Network unavailable"
    ) : NetworkException(NetworkError.NetworkUnavailable(message), message)
}