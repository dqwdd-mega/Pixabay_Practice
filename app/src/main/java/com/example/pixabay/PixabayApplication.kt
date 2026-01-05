package com.example.pixabay

import android.app.Application
import com.pixabay.core.data.local.datasource.PixabayCacheDataSource
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PixabayApplication : Application() {
    
    @Inject
    lateinit var pixabayCacheDataSource: PixabayCacheDataSource
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun onCreate() {
        super.onCreate()
        
        applicationScope.launch {
            pixabayCacheDataSource.cleanupExpiredCaches()
        }
    }
}