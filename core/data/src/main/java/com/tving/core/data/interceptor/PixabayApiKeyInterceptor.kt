package com.tving.core.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class PixabayApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()
        
        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()
        
        return chain.proceed(newRequest)
    }
}