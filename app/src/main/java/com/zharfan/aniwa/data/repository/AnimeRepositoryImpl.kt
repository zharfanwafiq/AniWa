package com.zharfan.aniwa.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zharfan.aniwa.data.api.ApiService
import com.zharfan.aniwa.data.response.detailanime.Data
import com.zharfan.aniwa.data.response.detailanime.DetailAnimeResponse
import com.zharfan.aniwa.data.response.detailanime.EpisodesListItem
import com.zharfan.aniwa.data.response.topweekly.DataItem
import com.zharfan.aniwa.data.response.topweekly.TopWeeklyAnimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeRepositoryImpl private constructor(
    private val apiService: ApiService
) : AnimeRepository {

    private val listTopWeeklyAnime = MutableLiveData<Result<List<DataItem>>>()

    private val detailAnime = MutableLiveData<Result<Data>>()

    private val listEpisode = MutableLiveData<Result<List<EpisodesListItem>>>()


    override fun showRecentAnimeList(): LiveData<Result<List<DataItem>>> {
        listTopWeeklyAnime.value = Result.Loading
        apiService.getTopWeekly()
            .enqueue(object : Callback<TopWeeklyAnimeResponse> {
                override fun onResponse(
                    call: Call<TopWeeklyAnimeResponse>,
                    response: Response<TopWeeklyAnimeResponse>
                ) {

                    if (response.isSuccessful) {
                        val listAnime = response.body()?.data
                        listTopWeeklyAnime.postValue(Result.Success(listAnime as List<DataItem>))

                    }
                }

                override fun onFailure(call: Call<TopWeeklyAnimeResponse>, t: Throwable) {
                    Log.e("animeFailed", "${t.printStackTrace()}")
                }
            })
        return listTopWeeklyAnime
    }

    override fun showDetailAnime(animeId: String): LiveData<Result<Data>> {
        detailAnime.value = Result.Loading
        apiService.getDetailAnime(animeId)
            .enqueue(object : Callback<DetailAnimeResponse> {
                override fun onResponse(
                    call: Call<DetailAnimeResponse>,
                    response: Response<DetailAnimeResponse>
                ) {

                    if (response.isSuccessful) {
                        val listDetailAnime = response.body()?.data
                        detailAnime.postValue(Result.Success(listDetailAnime as Data))

                        val listEpisodeAnime = response.body()?.data?.episodesList
                        listEpisode.postValue(Result.Success(listEpisodeAnime as List<EpisodesListItem>))
                    }
                }

                override fun onFailure(call: Call<DetailAnimeResponse>, t: Throwable) {
                    Log.e("animeDetailFailed", "onFailure: ${t.message}")
                }
            })
        return detailAnime
    }

    companion object {
        @Volatile
        private var instance: AnimeRepositoryImpl? = null
        fun getInstance(
            apiService: ApiService,
        ): AnimeRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: AnimeRepositoryImpl(apiService)
            }.also { instance = it }
    }
}