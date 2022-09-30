package com.zharfan.aniwa.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zharfan.aniwa.data.entity.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 2)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun recentAnimeDao(): AnimeDao

    companion object {
        @Volatile
        private var instance: AnimeDatabase? = null
        fun getInstance(context: Context): AnimeDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java, "RecentAnime.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
    }
}