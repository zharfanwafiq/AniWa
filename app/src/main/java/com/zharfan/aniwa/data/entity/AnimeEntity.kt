package com.zharfan.aniwa.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recentAnime")
class AnimeEntity(
    @field:ColumnInfo(name = "animeId")
    @field:PrimaryKey
    val animeId: String,

    @field:ColumnInfo(name = "animeTitle")
    val animeTitle: String,

    @field:ColumnInfo(name = "animeImg")
    val animeImg: String
)