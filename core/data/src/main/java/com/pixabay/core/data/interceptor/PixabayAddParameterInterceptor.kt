package com.pixabay.core.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class PixabayAddParameterInterceptor(
    private val apiKey: String,
    private val lang: String,
    private val safeSearch: Boolean,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithParameter = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .addQueryParameter("lang", lang)
            .addQueryParameter("safesearch", safeSearch.toString())
            .build()
        
        val newRequest = originalRequest.newBuilder()
            .url(urlWithParameter)
            .build()
        
        return chain.proceed(newRequest)
    }
}