package com.zharfan.aniwa.data.viewmodel.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zharfan.aniwa.data.api.ApiConfig
import com.zharfan.aniwa.data.response.detailanime.Data
import com.zharfan.aniwa.data.response.detailanime.DetailAnimeResponse
import com.zharfan.aniwa.data.response.detailanime.EpisodesListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel:ViewModel() {

    private val client = ApiConfig.getApiService()

    private val _detailAnime = MutableLiveData<Data>()
    val detailAnime: LiveData<Data> = _detailAnime

    private val _listEpisode = MutableLiveData<List<EpisodesListItem>>()
    val listEpisode: LiveData<List<EpisodesListItem>> = _listEpisode

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showDetailAnime(animeId: String) {
        _isLoading.value = true
        client.getDetailAnime(animeId)
            .enqueue(object :Callback<DetailAnimeResponse>{
                override fun onResponse(
                    call: Call<DetailAnimeResponse>,
                    response: Response<DetailAnimeResponse>
                ) {
                    if (response.isSuccessful){
                        _isLoading.value = false
                        _detailAnime.value = response.body()?.data
                        _listEpisode.value = response.body()?.data?.episodesList
                    }
                }

                override fun onFailure(call: Call<DetailAnimeResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("animeDetailFailed","onFailure: ${t.message}")
                }

            })
    }



}