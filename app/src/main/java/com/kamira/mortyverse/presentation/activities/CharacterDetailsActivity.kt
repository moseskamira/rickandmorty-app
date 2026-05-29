package com.kamira.mortyverse.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kamira.mortyverse.R
import com.kamira.mortyverse.data.network.ApiService
import com.kamira.mortyverse.data.repositories.CharacterRepositoryImpl
import com.kamira.mortyverse.databinding.ActivityCharacterDetailsBinding
import com.kamira.mortyverse.domain.models.Character
import com.kamira.mortyverse.domain.models.Episode
import com.kamira.mortyverse.presentation.extensions.applyToolbarInsets
import com.kamira.mortyverse.presentation.extensions.show
import com.kamira.mortyverse.presentation.factory.AppViewModelFactory
import com.kamira.mortyverse.presentation.viewModel.CharacterViewModel
import kotlinx.coroutines.launch

class CharacterDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharacterDetailsBinding
    private lateinit var viewModel: CharacterViewModel
    private val episodes = mutableListOf<Episode>()
    companion object {
        const val EXTRA_CHARACTER = "character"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupEpisodesButton()
        initializeViewModel()
        observeViewModel()
        initializeCharacter()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setSupportActionBar(this)
            applyToolbarInsets()
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.character_details)
        }
    }

    private fun setupEpisodesButton() {
        binding.episodesButton.apply {
            isEnabled = false
            text = getString(R.string.please_wait)
            setOnClickListener {
                if (episodes.isNotEmpty()) {
                    EpisodeBottomSheet(
                        episodes = episodes
                    ).show(
                        supportFragmentManager,
                        EpisodeBottomSheet::class.java.simpleName
                    )
                }
            }
        }
    }

    private fun initializeViewModel() {
        val apiClient = ApiService.apiClient
        val repository = CharacterRepositoryImpl(apiClient)
        val factory = AppViewModelFactory(
            movieRepo = repository
        )
        viewModel = ViewModelProvider(
            this,
            factory
        )[CharacterViewModel::class.java]
    }

    private fun initializeCharacter() {
        val character =
            intent.getParcelableExtra<Character>(EXTRA_CHARACTER)
        bindData(character)
    }

    private fun observeViewModel() {
        viewModel.characterEpisodesLoading.observe(this) { isLoading ->
            binding.episodesProgIndicator.show(isLoading)
        }
        viewModel.characterEpisodes.observe(this) { list ->
            episodes.clear()
            episodes.addAll(list)
            binding.episodesButton.apply {
                isEnabled = true
                text = getString(R.string.view_episodes)
            }
            binding.episodeCount.text =
                getString(
                    R.string.episode_count,
                    episodes.size
                )
        }
    }

    private fun bindData(character: Character?) {
        if (character == null) return
        binding.apply {
            movieName.text =
                character.name.orEmpty()
            movieStatus.text =
                character.status ?: getString(R.string.unknown)
            movieSpeciesGender.text =
                getString(
                    R.string.species_gender_format,
                    character.species ?: "-",
                    character.gender ?: "-"
                )
            movieOrigin.text =
                character.origin?.name
                    ?: getString(R.string.unknown)
            movieLocation.text =
                character.location?.name
                    ?: getString(R.string.unknown)
        }

        Glide.with(this)
            .load(character.image)
            .into(binding.movieImage)
        fetchEpisodes(character)
    }

    private fun fetchEpisodes(character: Character) {
        val episodeUrls =
            character.episode.orEmpty()
        if (episodeUrls.isEmpty()) return
        binding.episodesProgIndicator.show(true)
        lifecycleScope.launch {
            viewModel.getCharacterEpisodes(
                urls = episodeUrls
            )
        }
    }
}