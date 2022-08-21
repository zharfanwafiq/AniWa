package com.zharfan.aniwa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zharfan.aniwa.data.response.detailanime.EpisodesListItem
import com.zharfan.aniwa.databinding.EpisodeListBinding

class ListEpisodeAdapter :
    ListAdapter<EpisodesListItem, ListEpisodeAdapter.ViewHolder>(listEpisodeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListEpisodeAdapter.ViewHolder {
        return ViewHolder(
            EpisodeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListEpisodeAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: EpisodeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodesListItem: EpisodesListItem) = with(binding) {
            tvAnimeTitle.text = episodesListItem.episodeNum
        }
    }

    companion object {
        val listEpisodeDiffCallback = object : DiffUtil.ItemCallback<EpisodesListItem>() {
            override fun areItemsTheSame(
                oldItem: EpisodesListItem,
                newItem: EpisodesListItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EpisodesListItem,
                newItem: EpisodesListItem
            ): Boolean {
                return oldItem.episodeId == newItem.episodeId
            }

        }
    }

}