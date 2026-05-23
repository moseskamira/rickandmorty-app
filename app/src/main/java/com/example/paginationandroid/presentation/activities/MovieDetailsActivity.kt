package com.example.paginationandroid.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.paginationandroid.R
import com.example.paginationandroid.databinding.ActivityMovieDetailsBinding
import com.example.paginationandroid.domain.models.Movie

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movie = if (android.os.Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("movie", Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("movie")
        }
        bindData(movie = movie)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun bindData(movie: Movie?) {
        movie?.let {
            binding.movieName.text = it.name ?: "Unknown"
            binding.movieStatus.text = getString(R.string.status_label, it.status ?: "Unknown")
            binding.movieSpeciesGender.text = getString(
                R.string.species_gender,
                it.species ?: "-",
                it.gender ?: "-"
            )
            binding.movieOrigin.text = getString(
                R.string.origin_label,
                it.origin?.name ?: "Unknown"
            )
            binding.movieLocation.text = getString(
                R.string.location_label,
                it.location?.name ?: "Unknown"
            )
            Glide.with(this)
                .load(it.image)
                .into(binding.movieImage)
        }

    }
}