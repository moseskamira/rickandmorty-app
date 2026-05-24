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
import com.example.paginationandroid.domain.models.Episode
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.domain.repositories.MovieRepository

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    private val _episode = MutableLiveData<Episode>()
    val movie: LiveData<Movie> = _movie
    val episode: LiveData<Episode> = _episode
    private val _isMovieLoading = MutableLiveData(false)
    val isMovieLoading: LiveData<Boolean> = _isMovieLoading
    private val _movieError = MutableLiveData<String>("")
    val movieError: LiveData<String> = _movieError
    private val movieCache = mutableMapOf<Int, Movie>()
    private val episodeCache = mutableMapOf<String, Episode>()


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
       movieCache[id]?.let {
           _movie.value = it
           return
       }
       _isMovieLoading.value = true
       _movieError.value = ""
        val response = repo.getSingleMovie(id)
        if (response.success) {
            val data = response.data
            data?.let {
                movieCache[id] = it
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

    suspend fun getEpisode(url: String) {
        episodeCache[url]?.let {
            _episode.value = it
            return
        }
//        _isMovieLoading.value = true
//        _movieError.value = ""
        val response = repo.getSingleEpisode(url = url)
        if (response.success) {
            val data = response.data
            data?.let {
                episodeCache[url] = it
                _episode.value = it
            }
        } else {
            val error = response.error
            error?.let {
//                _movieError.value = it
            }
        }
//        _isMovieLoading.value = false


    }
}