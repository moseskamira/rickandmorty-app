package com.example.paginationandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paginationandroid.data.network.ApiClient
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.repositories.CharacterRepositoryImpl
import com.example.paginationandroid.databinding.ActivityCharacterBinding
import com.example.paginationandroid.presentation.activities.CharacterDetailsActivity
import com.example.paginationandroid.presentation.adapter.CharacterPagingAdapter
import com.example.paginationandroid.presentation.factory.AppViewModelFactory
import com.example.paginationandroid.presentation.viewModel.CharacterViewModel
import com.google.android.material.textfield.TextInputLayout

class CharacterActivity : AppCompatActivity() {
    private lateinit var movieAdapter: CharacterPagingAdapter
    private lateinit var errorDisplay: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.mainToolbar
        setSupportActionBar(toolbar)
        errorDisplay = binding.errorDisplayLayout
        progressBar = binding.progressBar
        initializeRecyclerView()
        initializeViewModel()
    }

    private fun initializeRecyclerView() {
        movieAdapter = CharacterPagingAdapter({ movie ->
            movie.id?.let { moveToMovieDetailsActivity(it) }
        })
        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CharacterActivity)
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
        val repo = CharacterRepositoryImpl(apiClient = apiClient)
        val factory = AppViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, factory = factory)[CharacterViewModel::class.java]
        viewModel.returnMovies().observe(this) { items ->
            movieAdapter.submitData(this.lifecycle, items)
        }
    }

    private fun moveToMovieDetailsActivity(movieId: Int) {
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }


}