package com.zharfan.aniwa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.zharfan.aniwa.R
import com.zharfan.aniwa.adapter.ListEpisodeAdapter
import com.zharfan.aniwa.data.response.detailanime.Data
import com.zharfan.aniwa.data.response.detailanime.EpisodesListItem
import com.zharfan.aniwa.data.viewmodel.main.DetailViewModel
import com.zharfan.aniwa.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

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

        initBundle()
        initObserve()

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

        setRecycleView()

    }

    private fun setRecycleView()= with(binding){
        listEpisodeAdapter = ListEpisodeAdapter()
        with(rvListEpisode){
            layoutManager = GridLayoutManager(requireContext(),4)
            setHasFixedSize(true)
            adapter = listEpisodeAdapter
        }
    }

    private fun setData(episodesList: List<EpisodesListItem>){
        if (episodesList.isNotEmpty()){
            listEpisodeAdapter.submitList(episodesList)
        }

    }

    private fun initObserve() {

        viewModel.showDetailAnime(animeId)
        viewModel.detailAnime.observe(viewLifecycleOwner) {
            showDetailData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.listEpisode.observe(viewLifecycleOwner){
            setData(it)
        }

    }


    private fun showLoading(isShow: Boolean) = with(binding) {
        progressBar.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


    companion object {
        const val ANIME_ID = "anime-id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}