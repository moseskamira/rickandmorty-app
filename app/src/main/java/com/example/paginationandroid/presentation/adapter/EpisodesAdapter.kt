package com.example.paginationandroid.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationandroid.databinding.ItemEpisodeBinding

class EpisodesAdapter(
    private val episodes: List<String>, private val onClick: (String) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEpisodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = episodes[position]
        holder.bindData(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class ViewHolder(private val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(epi: String) {
            binding.episodeText.text = epi
        }
    }

    override fun getItemCount() = episodes.size
}