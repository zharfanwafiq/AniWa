package com.zharfan.aniwa.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.zharfan.aniwa.utils.Common.isNetworkEnabled


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


        initBundle()
        checkConnectionEnabled()
    }

    private fun checkConnectionEnabled() {
        if (isNetworkEnabled(requireContext())) {
            setRecycleView()
            initObserve()
        } else {
            showNoNetworkConnection(true)
        }
    }

    private fun showNoNetworkConnection(isShow: Boolean) = with(binding) {
        if (isShow) {
            shimmerLoadingMain.visibility = View.GONE
            rvListEpisode.visibility = View.GONE
            layoutNoConnectionVisibility.visibility = View.VISIBLE
            layoutNoConnection.btnRetry.setOnClickListener {
                checkConnectionEnabled()
            }
        } else {
            layoutNoConnectionVisibility.visibility = View.GONE
        }

    }

    private fun initObserve() {
        val factory: MainViewModelFactory = MainViewModelFactory.getInstance(requireContext())
        val viewModel: DetailViewModel by viewModels {
            factory
        }

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
                    Common.showToast(requireContext(), "Terjadi Kesalahan ${it.error}")
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

    private fun showLoading(isShow: Boolean) = with(binding) {
        if (isShow) {
            shimmerLoadingMain.visibility = View.VISIBLE
            rvListEpisode.visibility = View.GONE
            layoutDetailVisibility.visibility = View.GONE
        } else {
            shimmerLoadingMain.visibility = View.GONE
            rvListEpisode.visibility = View.VISIBLE
            layoutDetailVisibility.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    companion object {
        const val ANIME_ID = "anime-id"
    }
}