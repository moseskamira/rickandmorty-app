package com.example.paginationandroid.presentation.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.repositories.MovieRepositoryImpl
import com.example.paginationandroid.databinding.ActivityMovieDetailsBinding
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.presentation.factory.AppViewModelFactory
import com.example.paginationandroid.presentation.viewModel.MovieViewModel
import kotlinx.coroutines.launch

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        initializeViewModel()
        observeViewModel()
        fetchMovie()
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
        val repository = MovieRepositoryImpl(apiClient)
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
            bindData(movie)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindData(movie: Movie?) {
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}