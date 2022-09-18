package com.zharfan.aniwa.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.zharfan.aniwa.data.entity.RecentAnimeEntity

@Dao
interface RecentAnimeDao {

    @Query("SELECT * FROM recentAnime ORDER BY animeTitle DESC")
    fun getRecentAnime(): LiveData<List<RecentAnimeEntity>>
}