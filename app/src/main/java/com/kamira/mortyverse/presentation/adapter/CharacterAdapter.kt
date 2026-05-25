package com.kamira.mortyverse.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kamira.mortyverse.databinding.CustomMoviesLayoutBinding
import com.kamira.mortyverse.domain.models.Character

class CharacterAdapter(
    private var characters: List<Character>,
    private val onTap: (Character) -> Unit
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
        val character = characters[position]
        holder.bindData(character)
        holder.itemView.setOnClickListener {
            onTap(character)
        }

    }

    fun updateData(newCharacters: List<Character>) {
        val oldSize = characters.size
        val newSize = newCharacters.size
        characters = newCharacters
        notifyItemRangeChanged(0, newSize)
        if (newSize > oldSize) {
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        }
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