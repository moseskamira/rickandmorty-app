package com.character.mobile.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.character.mobile.domain.repositories.CharacterRepository
import com.character.mobile.presentation.viewModel.CharacterViewModel

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