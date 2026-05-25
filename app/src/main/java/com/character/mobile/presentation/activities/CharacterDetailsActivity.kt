package com.character.mobile.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.character.mobile.R
import com.character.mobile.data.network.ApiService
import com.character.mobile.data.repositories.CharacterRepositoryImpl
import com.character.mobile.databinding.ActivityCharacterDetailsBinding
import com.character.mobile.domain.models.Character
import com.character.mobile.domain.models.Episode
import com.character.mobile.presentation.factory.AppViewModelFactory
import com.character.mobile.presentation.viewModel.CharacterViewModel
import kotlinx.coroutines.launch

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding
    private lateinit var viewModel: CharacterViewModel
    private lateinit var episodesButton: Button
    private val episodes = mutableListOf<Episode>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        episodesButton = binding.episodesButton
        episodesButton.isEnabled = false
        episodesButton.text = getString(R.string.please_wait)
        episodesButton.setOnClickListener {
            if (episodes.isEmpty()) {
                return@setOnClickListener
            }
            EpisodeBottomSheet(episodes = episodes).show(supportFragmentManager, "MyBottomSheet")
        }
        setupToolbar()
        initializeViewModel()
        observeViewModel()
        fetchMovie()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Movie Details"
        }
    }

    private fun initializeViewModel() {
        val apiClient = ApiService().getRetrofitServiceApi()
        val repository = CharacterRepositoryImpl(apiClient)
        val factory = AppViewModelFactory(movieRepo = repository)
        viewModel = ViewModelProvider(
            this,
            factory
        )[CharacterViewModel::class.java]
    }

    private fun fetchMovie() {
        val movieId = intent.getIntExtra("movieId", -1)
        if (movieId == -1) {
            finish()
            return
        }
        lifecycleScope.launch {
            viewModel.getMovie(movieId)
        }
    }


    private fun observeViewModel() {
        viewModel.isMovieLoading.observe(this) { isLoading ->
            binding.detailProgIndicator.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.characterEpisodesLoading.observe(this) { isLoading ->
            binding.episodesProgIndicator.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.movie.observe(this) { movie ->
            bindData(movie)
            movie.episode?.let {
                binding.episodesProgIndicator.visibility = View.VISIBLE
                lifecycleScope.launch {
                    viewModel.getCharacterEpisodes(it)
                }
            }

        }
        viewModel.characterEpisodes.observe(this) { list ->
            episodesButton.isEnabled = true
            episodesButton.text = getString(R.string.view_episodes)
            episodes.clear()
            episodes.addAll(list)
            binding.episodeCount.text = "${episodes.size}"
        }
    }


    private fun bindData(movie: Character?) {
        movie?.let {
            binding.movieName.text =
                it.name ?: ""
            binding.movieStatus.text =
                it.status ?: "Unknown"
            binding.movieSpeciesGender.text =
                getString(
                    R.string.species_gender_format,
                    it.species ?: "-",
                    it.gender ?: "-"
                )
            binding.movieOrigin.text =
                it.origin?.name ?: "Unknown"
            binding.movieLocation.text =
                it.location?.name ?: "Unknown"
            Glide.with(this)
                .load(it.image)
                .into(binding.movieImage)
        }
    }


}