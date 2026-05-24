package com.example.paginationandroid.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paginationandroid.domain.repositories.CharacterRepository
import com.example.paginationandroid.presentation.viewModel.CharacterViewModel

class AppViewModelFactory(
    private val movieRepo: CharacterRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(movieRepo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}