package com.zharfan.aniwa.di

import android.content.Context
import com.zharfan.aniwa.data.api.ApiConfig
import com.zharfan.aniwa.data.repository.AnimeRepositoryImpl
import com.zharfan.aniwa.data.room.AnimeDatabase
import com.zharfan.aniwa.utils.recentanime.AppExecutors

object Injection {
    fun provideRepository(context: Context): AnimeRepositoryImpl {
        val apiService = ApiConfig.getApiService()
        val database = AnimeDatabase.getInstance(context)
        val animeDao = database.recentAnimeDao()
        val appExecutors = AppExecutors()
        return AnimeRepositoryImpl.getInstance(apiService, animeDao, appExecutors)
    }


}