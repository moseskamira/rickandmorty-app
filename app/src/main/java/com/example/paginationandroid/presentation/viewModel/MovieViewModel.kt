package com.example.paginationandroid.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.paginationandroid.data.paging.MoviePagingSource
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.domain.repositories.MovieRepository

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {
    fun returnMovies(
    ): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {
                MoviePagingSource(
                    repo
                )
            }).liveData.cachedIn(
            viewModelScope
        )
    }
}