package com.zharfan.aniwa.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zharfan.aniwa.R
import com.zharfan.aniwa.data.entity.AnimeEntity
import com.zharfan.aniwa.databinding.RecentAnimeListHomeBinding
import com.zharfan.aniwa.fragment.DetailFragment

class TopWeeklyAdapter :
    ListAdapter<AnimeEntity, TopWeeklyAdapter.ViewHolder>(topWeeklyAnimeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecentAnimeListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: RecentAnimeListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animeEntity: AnimeEntity) = with(binding) {
            tvAnimeTitle.text = animeEntity.animeTitle

            Glide.with(itemView.context)
                .load(animeEntity.animeImg)
                .into(imgAnimeRecent)

            gridRecentAnime.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(DetailFragment.ANIME_ID, animeEntity.animeId)
                it.findNavController().navigate(R.id.detailFragment, bundle)

            }
        }
    }

    companion object {
        val topWeeklyAnimeDiffCallback = object : DiffUtil.ItemCallback<AnimeEntity>() {
            override fun areItemsTheSame(oldItem: AnimeEntity, newItem: AnimeEntity): Boolean {
                return oldItem.animeId == newItem.animeId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: AnimeEntity, newItem: AnimeEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}