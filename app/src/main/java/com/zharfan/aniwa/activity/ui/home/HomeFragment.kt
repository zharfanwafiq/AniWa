package com.zharfan.aniwa.activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zharfan.aniwa.adapter.TopWeeklyAdapter
import com.zharfan.aniwa.data.response.topweekly.DataItem
import com.zharfan.aniwa.data.viewmodel.main.MainViewModel
import com.zharfan.aniwa.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var topWeeklyAdapter: TopWeeklyAdapter
    private val viewModel: MainViewModel by viewModels()

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

        initObserve()
        setRecycleView()
    }


    private fun initObserve() {
        viewModel.topWeeklyAnime.observe(viewLifecycleOwner) {
            setData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun setData(dataItem: List<DataItem>){
        if (dataItem.isNotEmpty()){
            topWeeklyAdapter.submitList(dataItem)
        }
    }

    private fun showLoading(isShow: Boolean) = with(binding) {
        progressBar.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}