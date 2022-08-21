package com.zharfan.aniwa.data.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zharfan.aniwa.data.api.ApiConfig
import com.zharfan.aniwa.data.response.topweekly.DataItem
import com.zharfan.aniwa.data.response.topweekly.TopWeeklyAnimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val client = ApiConfig.getApiService()

    private val _listTopWeeklyAnime = MutableLiveData<List<DataItem>>()
    val topWeeklyAnime: LiveData<List<DataItem>> = _listTopWeeklyAnime

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        showRecentAnimeList()
    }

    private fun showRecentAnimeList() {
        _isLoading.value = true
        client.getTopWeekly()
            .enqueue(object : Callback<TopWeeklyAnimeResponse> {
                override fun onResponse(
                    call: Call<TopWeeklyAnimeResponse>,
                    response: Response<TopWeeklyAnimeResponse>
                ) {
                    if (response.isSuccessful){
                        _isLoading.value = false
                        _listTopWeeklyAnime.value = response.body()?.data
                    }
                }

                override fun onFailure(call: Call<TopWeeklyAnimeResponse>, t: Throwable) {
                    Log.e("animeFailed", "onFailure: ${t}", )
                }
            })
    }
}