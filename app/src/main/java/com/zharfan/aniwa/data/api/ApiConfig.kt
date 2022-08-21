package com.zharfan.aniwa.data.api

import com.zharfan.aniwa.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    fun getApiService():ApiService{
        val interceptor = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(UrlEndPoint.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}