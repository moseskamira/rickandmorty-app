package com.example.paginationandroid.presentation.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.paginationandroid.R
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.repositories.CharacterRepositoryImpl
import com.example.paginationandroid.databinding.ActivityCharacterDetailsBinding
import com.example.paginationandroid.domain.models.Character
import com.example.paginationandroid.domain.models.Episode
import com.example.paginationandroid.presentation.factory.AppViewModelFactory
import com.example.paginationandroid.presentation.viewModel.CharacterViewModel
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