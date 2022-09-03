package com.zharfan.aniwa.data.viewmodel.main

import androidx.lifecycle.ViewModel
import com.zharfan.aniwa.data.repository.AnimeRepository

class DetailViewModel(private val animeRepository: AnimeRepository) : ViewModel() {

    fun getDetailAnime(animeId: String) = animeRepository.showDetailAnime(animeId)

}