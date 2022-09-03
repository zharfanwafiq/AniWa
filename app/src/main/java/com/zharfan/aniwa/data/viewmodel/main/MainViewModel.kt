package com.zharfan.aniwa.data.viewmodel.main

import androidx.lifecycle.ViewModel
import com.zharfan.aniwa.data.repository.AnimeRepository

class MainViewModel(recentAnimeRepository: AnimeRepository) : ViewModel() {
    val getRecentAnime = recentAnimeRepository.showRecentAnimeList()
}