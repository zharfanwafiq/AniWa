package com.zharfan.aniwa.activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zharfan.aniwa.R
import com.zharfan.aniwa.adapter.TopWeeklyAdapter
import com.zharfan.aniwa.data.repository.Result
import com.zharfan.aniwa.data.viewmodel.main.MainViewModel
import com.zharfan.aniwa.databinding.FragmentHomeBinding
import com.zharfan.aniwa.factory.MainViewModelFactory
import com.zharfan.aniwa.utils.Common
import com.zharfan.aniwa.utils.Common.isNetworkEnabled

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private var topWeeklyAdapter: TopWeeklyAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnectionEnabled()
        setSwipeRefresh()
    }

    private fun setSwipeRefresh() = with(binding) {
        with(swipeRefreshMain) {
            setColorSchemeColors(R.color.purple_700)
            setOnRefreshListener { checkConnectionEnabled() }
        }
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
            rvRecentAnime.visibility = View.GONE
            swipeRefreshMain.isRefreshing = false
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
        val viewModel: MainViewModel by viewModels {
            factory
        }

        viewModel.getRecentAnime.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        topWeeklyAdapter?.submitList(it.data)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        Common.showToast(requireContext(), "Terjadi Kesalahan ${it.error}")

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        topWeeklyAdapter = null
    }

    private fun setRecycleView() {
        topWeeklyAdapter = TopWeeklyAdapter()
        binding.apply {
            with(rvRecentAnime) {
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                setHasFixedSize(true)
                adapter = topWeeklyAdapter
            }
        }
    }

    private fun showLoading(isShow: Boolean) = with(binding) {
        if (isShow) {
            shimmerLoadingMain.visibility = View.VISIBLE
            rvRecentAnime.visibility = View.GONE
            swipeRefreshMain.isRefreshing = true
        } else {
            shimmerLoadingMain.visibility = View.GONE
            rvRecentAnime.visibility = View.VISIBLE
            swipeRefreshMain.isRefreshing = false
        }
    }
}