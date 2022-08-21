package com.zharfan.aniwa.data.response.recentrelease

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RecentAnime(

	@field:SerializedName("animeImg")
	val animeImg: String,

	@field:SerializedName("episodeNum")
	val episodeNum: String,

	@field:SerializedName("subOrDub")
	val subOrDub: String,

	@field:SerializedName("episodeUrl")
	val episodeUrl: String,

	@field:SerializedName("episodeId")
	val episodeId: String,

	@field:SerializedName("animeTitle")
	val animeTitle: String
) : Parcelable