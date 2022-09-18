package com.zharfan.aniwa.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zharfan.aniwa.data.entity.RecentAnimeEntity

@Database(entities = [RecentAnimeEntity::class], version = 1)
abstract class RecentAnimeDatabase:RoomDatabase() {
    abstract fun recentAnimeDao(): RecentAnimeDao

    companion object{
        @Volatile
        private var instance: RecentAnimeDatabase? = null
        fun getInstance(context: Context): RecentAnimeDatabase =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecentAnimeDatabase::class.java,"RecentAnime.db"
                ).build()
            }
    }
}