package com.example.paginationandroid.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationandroid.R

class EpisodesAdapter(
    private val episodes: List<String>, private val onClick: (String) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_episode, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = episodes[position]
        holder.bindImage(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val episodeView: TextView = view.findViewById(R.id.episode_text)
        fun bindImage(epi: String) {
            episodeView.text = epi

        }
    }

    override fun getItemCount() = episodes.size
}