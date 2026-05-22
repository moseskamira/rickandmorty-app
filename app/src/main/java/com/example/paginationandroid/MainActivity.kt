package com.example.paginationandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationandroid.data.network.ApiClient
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.repositories.MovieRepositoryImpl
import com.example.paginationandroid.presentation.adapter.MovieAdapter
import com.example.paginationandroid.presentation.factory.AppViewModelFactory
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

        initializeRecyclerView()
        initializeViewModel()
    }

    private fun initializeRecyclerView() {
        movieAdapter = MovieAdapter()
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
        movieAdapter.addLoadStateListener { loadState ->
            progressBar.visibility = if (loadState.refresh is LoadState.Loading)
                View.VISIBLE
            else
                View.GONE
            val errorState =
                loadState.refresh as? LoadState.Error
            errorState?.let {
                errorDisplay.error =
                    it.error.localizedMessage
            }
        }

    }

    private fun initializeViewModel() {
        val apiClient: ApiClient = ApiService().getRetrofitServiceApi()
        val repo = MovieRepositoryImpl(apiClient = apiClient)
        val factory = AppViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, factory = factory)[MovieViewModel::class.java]
        viewModel.returnMovies().observe(this) { items ->
            movieAdapter.submitData(this.lifecycle, items)
        }
    }


}