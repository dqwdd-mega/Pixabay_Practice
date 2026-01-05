package com.pixabay.core.data.interceptor

import com.pixabay.core.common.exception.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class HttpErrorInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        val response = try {
            chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            throw NetworkException.TimeoutException("Connection timeout")
        } catch (e: IOException) {
            throw NetworkException.NetworkUnavailableException("Network unavailable")
        }
        
        if (!response.isSuccessful) {
            val errorBody = response.body?.string() ?: "Unknown error"
            
            when (response.code) {
                429 -> {
                    // Rate Limit - Retry-After 헤더가 있으면 파싱
                    val retryAfter = response.header("Retry-After")?.toLongOrNull()
                    throw NetworkException.RateLimitException(
                        retryAfter = retryAfter,
                        message = errorBody.ifBlank { "API rate limit exceeded" }
                    )
                }
                400 -> {
                    throw NetworkException.BadRequestException(
                        message = errorBody.ifBlank { "Bad request" }
                    )
                }
                401 -> {
                    throw NetworkException.UnauthorizedException(
                        message = errorBody.ifBlank { "Unauthorized" }
                    )
                }
                403 -> {
                    throw NetworkException.ForbiddenException(
                        message = errorBody.ifBlank { "Forbidden" }
                    )
                }
                404 -> {
                    throw NetworkException.NotFoundException(
                        message = errorBody.ifBlank { "Not found" }
                    )
                }
                in 500..599 -> {
                    throw NetworkException.ServerException(
                        code = response.code,
                        message = errorBody.ifBlank { "Server error" }
                    )
                }
                else -> {
                    throw NetworkException.ServerException(
                        code = response.code,
                        message = errorBody.ifBlank { "Unknown error" }
                    )
                }
            }
        }
        
        return response
    }
}