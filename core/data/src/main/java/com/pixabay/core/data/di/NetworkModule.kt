package com.pixabay.core.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pixabay.core.data.interceptor.HttpErrorInterceptor
import com.pixabay.core.data.interceptor.PixabayAddParameterInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun providePixabayApiKeyInterceptor(): PixabayAddParameterInterceptor {
        return PixabayAddParameterInterceptor(
            apiKey = com.pixabay.core.data.BuildConfig.PIXABAY_API_KEY,
            lang = "ko",
            safeSearch = true
        )
    }

    @Provides
    @Singleton
    fun provideHttpErrorInterceptor(): HttpErrorInterceptor {
        return HttpErrorInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        addParameterInterceptor: PixabayAddParameterInterceptor,
        httpErrorInterceptor: HttpErrorInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(addParameterInterceptor)
            .addInterceptor(httpErrorInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pixabay.com/") // Base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}