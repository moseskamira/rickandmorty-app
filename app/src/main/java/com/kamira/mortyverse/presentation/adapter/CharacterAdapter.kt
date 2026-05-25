package com.kamira.mortyverse.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamira.mortyverse.databinding.CustomMoviesLayoutBinding
import com.kamira.mortyverse.domain.models.Character

class CharacterAdapter(
    private var characters: List<Character>
) : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomMoviesLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(characters[position])
    }
    fun updateData(newCharacters: List<Character>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    class MyViewHolder(
        private val binding: CustomMoviesLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Character) {
            binding.characterNameTv.text = item.name
            binding.characterSpeciesTv.text = item.species
            binding.characterTypeTv.text = item.type
            val imageView = binding.movieImage
            Glide.with(imageView).asBitmap().load(item.image)
                .into(imageView)
        }
    }

    override fun getItemCount() = characters.size
}