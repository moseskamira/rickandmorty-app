package com.kamira.mortyverse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.kamira.mortyverse.data.network.ApiClient
import com.kamira.mortyverse.data.network.ApiService
import com.kamira.mortyverse.data.repositories.CharacterRepositoryImpl
import com.kamira.mortyverse.databinding.ActivityCharacterBinding
import com.kamira.mortyverse.domain.models.Character
import com.kamira.mortyverse.presentation.activities.CharacterDetailsActivity
import com.kamira.mortyverse.presentation.adapter.CharacterPagingAdapter
import com.kamira.mortyverse.presentation.extensions.applyToolbarInsets
import com.kamira.mortyverse.presentation.factory.AppViewModelFactory
import com.kamira.mortyverse.presentation.viewModel.CharacterViewModel

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
        toolbar.applyToolbarInsets()
        errorDisplay = binding.errorDisplayLayout
        progressBar = binding.progressBar
        initializeRecyclerView()
        initializeViewModel()
    }

    private fun initializeRecyclerView() {
        movieAdapter = CharacterPagingAdapter({ character ->
            loadCharacterDetails(character)
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

    private fun loadCharacterDetails(character:Character) {
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra("character", character)
        startActivity(intent)
    }


}