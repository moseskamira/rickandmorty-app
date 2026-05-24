package com.example.paginationandroid.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paginationandroid.R
import com.example.paginationandroid.domain.models.Character
import de.hdodenhof.circleimageview.CircleImageView

class MovieAdapter(private val onMovieClick: (Character) -> Unit) : PagingDataAdapter<Character, MovieAdapter.MyViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_movies_layout, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {movie-> holder.bindMovieData(movie)
            holder.itemView.setOnClickListener{
                onMovieClick(movie)
            }
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieNameTv: TextView = view.findViewById(R.id.character_nameTv)
        private val characterSpeciesTv: TextView = view.findViewById(R.id.character_species_tv)
        private val movieImageView: CircleImageView = view.findViewById(R.id.movie_image)
        private val characterType: TextView = view.findViewById(R.id.character_type_tv)
        fun bindMovieData(data: Character) {
            movieNameTv.text = data.name
            characterSpeciesTv.text = data.species
            characterType.text = data.type
            Glide.with(movieImageView).asBitmap().load(data.image)
                .into(movieImageView)
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}