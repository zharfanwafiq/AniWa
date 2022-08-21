package com.zharfan.aniwa.data.response.detailanime

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Data(

	@field:SerializedName("releasedDate")
	val releasedDate: String,

	@field:SerializedName("otherNames")
	val otherNames: String,

	@field:SerializedName("episodesList")
	val episodesList: List<EpisodesListItem>,

	@field:SerializedName("animeImg")
	val animeImg: String,

	@field:SerializedName("totalEpisodes")
	val totalEpisodes: String,

	@field:SerializedName("genres")
	val genres: List<String>,

	@field:SerializedName("synopsis")
	val synopsis: String,

	@field:SerializedName("animeTitle")
	val animeTitle: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable