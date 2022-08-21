package com.zharfan.aniwa.data.response.recentrelease

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RecentAnimeResponse(

	@field:SerializedName("data")
	val data: List<RecentAnime>,

	@field:SerializedName("message")
	val message: String
) : Parcelable