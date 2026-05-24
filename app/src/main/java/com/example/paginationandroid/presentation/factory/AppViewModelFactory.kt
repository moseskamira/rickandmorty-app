package com.example.paginationandroid.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paginationandroid.domain.repositories.CharacterRepository
import com.example.paginationandroid.presentation.viewModel.MovieViewModel

class AppViewModelFactory(
    private val movieRepo: CharacterRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieRepo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}