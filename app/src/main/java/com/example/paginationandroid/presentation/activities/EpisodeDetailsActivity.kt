package com.example.paginationandroid.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.repositories.MovieRepositoryImpl
import com.example.paginationandroid.databinding.ActivityEpisodeDetailsBinding
import com.example.paginationandroid.domain.models.Episode
import com.example.paginationandroid.presentation.adapter.CharacterAdapter
import com.example.paginationandroid.presentation.factory.AppViewModelFactory
import com.example.paginationandroid.presentation.viewModel.MovieViewModel
import kotlinx.coroutines.launch

class EpisodeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEpisodeDetailsBinding
    private lateinit var viewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        initializeViewModel()
        observeViewModel()
        fetchEpisode()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun initializeViewModel() {
        val apiClient = ApiService().getRetrofitServiceApi()
        val repository = MovieRepositoryImpl(apiClient)
        val factory = AppViewModelFactory(movieRepo = repository)
        viewModel = ViewModelProvider(
            this,
            factory
        )[MovieViewModel::class.java]
    }

    private fun observeViewModel() {
//        viewModel.isMovieLoading.observe(this) { isLoading ->
//            binding.detailProgIndicator.visibility =
//                if (isLoading) View.VISIBLE else View.GONE
//        }
        viewModel.episode.observe(this) { episode ->
            bindData(episode)
        }
    }

    private fun bindData(episode: Episode?) {
        episode?.let {
            binding.tvEpisodeName.text =
                it.name
            binding.tvEpisodeCode.text =
                it.episode
            binding.tvAirDate.text =
               it.airDate
            binding.tvCreated.text =
                it.created
            binding.rvCharacters.apply {
                layoutManager = LinearLayoutManager(this@EpisodeDetailsActivity)
                adapter = CharacterAdapter(episode.characters)

            }
        }
    }

    private fun fetchEpisode() {
        val url = intent.getStringExtra("episode_link")
        if (url == null) {
            finish()
            return
        }
        lifecycleScope.launch {
            viewModel.getEpisode(url)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Episode Info"
        }
    }
}