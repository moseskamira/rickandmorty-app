package com.example.paginationandroid.presentation.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.repositories.CharacterRepositoryImpl
import com.example.paginationandroid.databinding.ActivityMovieDetailsBinding
import com.example.paginationandroid.domain.models.Character
import com.example.paginationandroid.presentation.factory.AppViewModelFactory
import com.example.paginationandroid.presentation.viewModel.MovieViewModel
import kotlinx.coroutines.launch

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var episodesButton: Button
    private val episodes = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        episodesButton = binding.episodesButton
        episodesButton.setOnClickListener {
            val bottomSheet = MyBottomSheet(episodes = episodes)
            bottomSheet.show(supportFragmentManager, "MyBottomSheet")
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
        )[MovieViewModel::class.java]
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
        viewModel.movie.observe(this) { movie ->
            val items = movie.episode
            if (items != null) {
                episodes.addAll(items)
            }
            bindData(movie)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(movie: Character?) {
        movie?.let {
            binding.movieName.text =
                it.name ?: "Unknown"
            binding.movieStatus.text =
                it.status ?: "Unknown"
            binding.movieSpeciesGender.text =
                "${it.species ?: "-"} | ${it.gender ?: "-"}"
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