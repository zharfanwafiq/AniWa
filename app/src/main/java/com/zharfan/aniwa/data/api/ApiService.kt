package com.zharfan.aniwa.data.api

import com.zharfan.aniwa.data.response.detailanime.DetailAnimeResponse
import com.zharfan.aniwa.data.response.recentrelease.RecentAnimeResponse
import com.zharfan.aniwa.data.response.topweekly.DataItem
import com.zharfan.aniwa.data.response.topweekly.TopWeeklyAnimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(UrlEndPoint.RECENT_RELEASE_URL)
    fun getRecentRelease(): Call<RecentAnimeResponse>

    @GET(UrlEndPoint.TOP_WEEKLY)
    fun getTopWeekly(): Call<TopWeeklyAnimeResponse>

    @GET(UrlEndPoint.DETAIL_URLS)
    fun getDetailAnime(
        @Path("animeId") animeId: String
    ): Call<DetailAnimeResponse>

    @GET(UrlEndPoint.ANIME_ID)
    fun getAllAnime(): Call<ArrayList<DataItem>>
}