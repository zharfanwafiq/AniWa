package com.zharfan.aniwa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.zharfan.aniwa.adapter.ListEpisodeAdapter
import com.zharfan.aniwa.data.repository.Result
import com.zharfan.aniwa.data.response.detailanime.Data
import com.zharfan.aniwa.data.response.detailanime.EpisodesListItem
import com.zharfan.aniwa.data.viewmodel.main.DetailViewModel
import com.zharfan.aniwa.databinding.FragmentDetailBinding
import com.zharfan.aniwa.factory.MainViewModelFactory
import com.zharfan.aniwa.utils.Common


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var animeId: String = ""

    private lateinit var listEpisodeAdapter: ListEpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: MainViewModelFactory = MainViewModelFactory.getInstance()
        val viewModel: DetailViewModel by viewModels {
            factory
        }

        initBundle()
        setRecycleView()

        viewModel.getDetailAnime(animeId).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    showDetailData(it.data)
                    setData(it.data.episodesList)
                }

                is Result.Error -> {
                    showLoading(false)
                    Common.showToast(requireContext(),"Terjadi Kesalahan ${it.error}")
                }
            }
        }


    }

    private fun initBundle() {
        val argument = arguments
        animeId = argument?.getString(ANIME_ID).toString()
    }


    private fun showDetailData(data: Data) = with(binding) {
        Glide.with(imgAnimeDetail)
            .load(data.animeImg)
            .into(imgAnimeDetail)

        tvAnimeTitle.text = data.animeTitle
        tvAnimeDescription.text = data.synopsis
        tvAnimeGenres.text = "Genres: ${data.genres}"
        tvAnimeStatus.text = "Status: ${data.status}"
        tvAnimeType.text = "Type: ${data.type}"
        tvAnimeReleaseDate.text = "Date Aired: ${data.releasedDate}"
        tvAnimeTotalEps.text = "Total Eps: ${data.totalEpisodes}"

    }

    private fun setRecycleView() = with(binding) {
        listEpisodeAdapter = ListEpisodeAdapter()
        with(rvListEpisode) {
            layoutManager = GridLayoutManager(requireContext(), 5)
            adapter = listEpisodeAdapter

        }
    }

    private fun setData(episodesList: List<EpisodesListItem>) {
        if (episodesList.isNotEmpty()) {
            listEpisodeAdapter.submitList(episodesList)
        }
    }

    private fun showLoading(isShow: Boolean) = binding.progressBar.isVisible == isShow

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {
        const val ANIME_ID = "anime-id"
    }
}