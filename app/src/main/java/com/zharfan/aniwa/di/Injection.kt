package com.zharfan.aniwa.di

import com.zharfan.aniwa.data.api.ApiConfig
import com.zharfan.aniwa.data.repository.AnimeRepositoryImpl

object Injection {
    fun provideRepository(): AnimeRepositoryImpl {
        val apiService = ApiConfig.getApiService()
        return AnimeRepositoryImpl.getInstance(apiService)
    }


}