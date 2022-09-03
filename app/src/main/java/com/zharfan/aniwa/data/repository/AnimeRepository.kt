package com.zharfan.aniwa.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zharfan.aniwa.data.response.detailanime.Data
import com.zharfan.aniwa.data.response.topweekly.DataItem

interface AnimeRepository {
    fun showRecentAnimeList(): LiveData<Result<List<DataItem>>>
    fun showDetailAnime(animeId: String) : LiveData<Result<Data>>
}