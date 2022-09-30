package com.zharfan.aniwa.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zharfan.aniwa.data.entity.AnimeEntity

@Dao
interface AnimeDao {

    @Query("SELECT * FROM recentAnime ORDER BY animeTitle DESC")
    fun getRecentAnime(): LiveData<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAnime(news: List<AnimeEntity>)
}