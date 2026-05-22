package com.example.paginationandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationandroid.presentation.adapter.MovieAdapter
import com.example.paginationandroid.presentation.viewModel.MovieViewModel
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var errorDisplay: TextInputLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieRecyclerView = findViewById(R.id.movie_recycler_view)
        errorDisplay = findViewById(R.id.error_display_layout)
        progressBar = findViewById(R.id.progress_bar)
        movieAdapter = MovieAdapter()
        initializeRecyclerView()
        initializeViewModel()
    }

    private fun initializeRecyclerView() {
        movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = movieAdapter
        }
    }

    private fun initializeViewModel() {
        val viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.returnMovies(errorDisplay, progressBar).observe(this) {
            movieAdapter.submitData(this.lifecycle, it)
        }
    }


}