package com.zharfan.aniwa.data.response.topweekly

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItem(

	@field:SerializedName("animeUrl")
	val animeUrl: String,

	@field:SerializedName("animeId")
	val animeId: String,

	@field:SerializedName("animeImg")
	val animeImg: String,

	@field:SerializedName("genres")
	val genres: @RawValue List<Any>,

	@field:SerializedName("animeTitle")
	val animeTitle: String
) : Parcelable