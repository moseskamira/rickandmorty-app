package com.example.paginationandroid.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie
    private val _isMovieLoading = MutableLiveData<Boolean>(false)
    val isMovieLoading: LiveData<Boolean> = _isMovieLoading
    private val _movieError = MutableLiveData<String>("")
    val movieError: LiveData<String> = _movieError


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

   suspend fun getMovie(id: Int) {
        _isMovieLoading.value = true
        _movieError.value = ""
        val response = repo.getSingleMovie(id)
        if (response.success) {
            val data = response.data
            data?.let {
                _movie.value = it
            }
        } else {
            val error = response.error
            error?.let {
                _movieError.value = it
            }
        }
        _isMovieLoading.value = false


    }
}