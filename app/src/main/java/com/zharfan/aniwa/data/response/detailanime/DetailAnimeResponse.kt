package com.zharfan.aniwa.data.response.detailanime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DetailAnimeResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
) : Parcelable