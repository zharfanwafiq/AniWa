package com.zharfan.aniwa.data.response.detailanime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class EpisodesListItem(

	@field:SerializedName("episodeNum")
	val episodeNum: String,

	@field:SerializedName("episodeUrl")
	val episodeUrl: String,

	@field:SerializedName("episodeId")
	val episodeId: String
) : Parcelable