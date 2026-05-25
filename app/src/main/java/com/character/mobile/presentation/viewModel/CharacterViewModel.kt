package com.character.mobile.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.character.mobile.data.paging.MoviePagingSource
import com.character.mobile.domain.models.Character
import com.character.mobile.domain.models.Episode
import com.character.mobile.domain.repositories.CharacterRepository

class CharacterViewModel(private val repo: CharacterRepository) : ViewModel() {
    private val _movie = MutableLiveData<Character>()
    private val _episodeCharacters = MutableLiveData<List<Character>>()
    private val _characterEpisodes = MutableLiveData<List<Episode>>()
    val characterEpisodes: LiveData<List<Episode>> = _characterEpisodes
    val episodeCharacters: LiveData<List<Character>> = _episodeCharacters
    private val _charactersError = MutableLiveData("")
    val charactersError: LiveData<String> = _charactersError
    val movie: LiveData<Character> = _movie
    private val _isMovieLoading = MutableLiveData(false)
    private val _isLoadingCharacters = MutableLiveData(false)
    private val _characterEpisodesLoading = MutableLiveData(false)
    val isLoadingCharacters: LiveData<Boolean> = _isLoadingCharacters
    val characterEpisodesLoading: LiveData<Boolean> = _characterEpisodesLoading
    val isMovieLoading: LiveData<Boolean> = _isMovieLoading
    private val _movieError = MutableLiveData("")
    val movieError: LiveData<String> = _movieError
    private val _characterEpisodesError = MutableLiveData("")
    val characterEpisodesError: LiveData<String> = _characterEpisodesError

    private val movieCache = mutableMapOf<Int, Character>()
    private val episodeCache = mutableMapOf<String, Episode>()


    fun returnMovies(
    ): LiveData<PagingData<Character>> {
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

    suspend fun getEpisodeCharacters(urls: List<String>) {
        _isLoadingCharacters.value = true
        _charactersError.value = ""
        try {
            val response = repo.getEpisodeCharacters(urls = urls)
            if (response.success) {
                _episodeCharacters.value = response.data ?: emptyList()
            } else {
                _charactersError.value = response.error ?: "Unknown error"
            }
        } catch (e: Exception) {
            _charactersError.value = e.message ?: "Network error"
        } finally {
            _isLoadingCharacters.value = false
        }
    }

    suspend fun getCharacterEpisodes(urls: List<String>) {
        _characterEpisodesLoading.value = true
        try {
            val response = repo.getCharacterEpisodes(urls = urls)
            if (response.success) {
                _characterEpisodes.value = response.data ?: emptyList()
            } else {
                val error = response.error
                _characterEpisodesError.value = error
            }
        } catch (e: Exception) {
            val error = e.message
            _characterEpisodesError.value = error
        } finally {
            _characterEpisodesLoading.value = false
        }
    }
}