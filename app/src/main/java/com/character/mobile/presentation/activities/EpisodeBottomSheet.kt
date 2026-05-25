package com.character.mobile.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.character.mobile.databinding.FragmentEpisodeBottomSheetBinding
import com.character.mobile.domain.models.Episode
import com.character.mobile.presentation.adapter.EpisodesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EpisodeBottomSheet(private val episodes: List<Episode>) : BottomSheetDialogFragment() {
    private var _binding: FragmentEpisodeBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.episodesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = EpisodesAdapter(episodes=episodes, onClick = { episode ->
                val intent = Intent(requireContext(), EpisodeDetailsActivity::class.java)
                intent.putExtra("episode", episode)
                startActivity(intent)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}