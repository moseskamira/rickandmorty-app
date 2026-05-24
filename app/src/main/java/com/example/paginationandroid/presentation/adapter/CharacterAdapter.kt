package com.example.paginationandroid.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationandroid.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    class MyViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: String) {
            binding.characterText.text = item
        }
    }

    override fun getItemCount() = items.size
}