package com.example.paginationandroid.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paginationandroid.databinding.FragmentMyBottomSheetBinding
import com.example.paginationandroid.presentation.adapter.EpisodesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MyBottomSheet(private val episodes: List<String>) : BottomSheetDialogFragment() {
    private var _binding: FragmentMyBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.episodesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EpisodesAdapter(episodes=episodes, onClick = { episode ->
                val intent = Intent(requireContext(), EpisodeDetailsActivity::class.java)
                intent.putExtra("episode_link", episode)
                startActivity(intent)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}