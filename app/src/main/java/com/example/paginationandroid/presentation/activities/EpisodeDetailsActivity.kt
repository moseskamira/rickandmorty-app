package com.example.paginationandroid.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paginationandroid.R
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.repositories.CharacterRepositoryImpl
import com.example.paginationandroid.databinding.ActivityEpisodeDetailsBinding
import com.example.paginationandroid.domain.models.Episode
import com.example.paginationandroid.presentation.adapter.CharacterAdapter
import com.example.paginationandroid.presentation.factory.AppViewModelFactory
import com.example.paginationandroid.presentation.viewModel.CharacterViewModel
import kotlinx.coroutines.launch

class EpisodeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEpisodeDetailsBinding
    private lateinit var viewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var episode: Episode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupRecycler()
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
        val repository = CharacterRepositoryImpl(apiClient)
        val factory = AppViewModelFactory(movieRepo = repository)
        viewModel = ViewModelProvider(
            this,
            factory
        )[CharacterViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.isLoadingCharacters.observe(this) { isCLoading ->
            binding.progressIndicator.visibility =
                if (isCLoading) View.VISIBLE else View.GONE
        }
        viewModel.episodeCharacters.observe(this) { characters ->
            binding.tvCharactersTitle.text =
                getString(R.string.characters_count, characters.size)
            characterAdapter.updateData(characters)
        }
    }

    private fun bindEpisode(episode: Episode?) {
        episode?.let {
            binding.tvEpisodeName.text = it.name
            binding.tvEpisodeCode.text = it.episode
            binding.tvAirDate.text = it.airDate
            binding.tvCreated.text = it.created
            lifecycleScope.launch {
                viewModel.getEpisodeCharacters(it.characters)
            }
        }
    }

    private fun setupRecycler() {
        characterAdapter = CharacterAdapter(emptyList())
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@EpisodeDetailsActivity)
            adapter = characterAdapter
        }
    }

    private fun fetchEpisode() {
        episode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("episode", Episode::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Episode>("episode")
        } ?: return
        bindEpisode(episode)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Episode Info"
        }
    }
}