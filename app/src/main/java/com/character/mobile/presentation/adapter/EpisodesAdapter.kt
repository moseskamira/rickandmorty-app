package com.character.mobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.character.mobile.databinding.ItemEpisodeBinding
import com.character.mobile.domain.models.Episode

class EpisodesAdapter(
    private val episodes: List<Episode>, private val onClick: (Episode) -> Unit
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
        fun bindData(epi: Episode) {
            binding.episodeText.text = epi.name
        }
    }

    override fun getItemCount() = episodes.size
}