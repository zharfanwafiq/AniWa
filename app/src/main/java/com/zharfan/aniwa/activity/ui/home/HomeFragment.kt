package com.zharfan.aniwa.activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zharfan.aniwa.adapter.TopWeeklyAdapter
import com.zharfan.aniwa.data.repository.Result
import com.zharfan.aniwa.data.viewmodel.main.MainViewModel
import com.zharfan.aniwa.databinding.FragmentHomeBinding
import com.zharfan.aniwa.factory.MainViewModelFactory

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
        val factory: MainViewModelFactory = MainViewModelFactory.getInstance()
        val viewModel: MainViewModel by viewModels {
            factory
        }

        setRecycleView()

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
                        Toast.makeText(
                            requireContext(),
                            "Terjadi Kesalahan ${it.error}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
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

    private fun showLoading(isShow: Boolean) = with(binding) {
        progressBar.visibility = if (isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}