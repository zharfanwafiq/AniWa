package com.zharfan.aniwa.data.repository

import androidx.lifecycle.LiveData
import com.zharfan.aniwa.data.entity.AnimeEntity
import com.zharfan.aniwa.data.response.detailanime.Data

interface AnimeRepository {
    fun showRecentAnimeList(): LiveData<Result<List<AnimeEntity>>>
    fun showDetailAnime(animeId: String): LiveData<Result<Data>>
}