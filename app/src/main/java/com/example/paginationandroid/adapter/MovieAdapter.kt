package com.example.paginationandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paginationandroid.R
import com.example.paginationandroid.model.Movie
import de.hdodenhof.circleimageview.CircleImageView

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MyViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_movies_layout, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieNameTv: TextView = view.findViewById(R.id.character_nameTv)
        private val characterSpeciesTv: TextView = view.findViewById(R.id.character_species_tv)
        private val movieImageView: CircleImageView = view.findViewById(R.id.movie_image)
        fun bind(data: Movie) {
            movieNameTv.text = data.name
            characterSpeciesTv.text = data.species
            Glide.with(movieImageView).asBitmap().load(data.image)
                .into(movieImageView)
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}