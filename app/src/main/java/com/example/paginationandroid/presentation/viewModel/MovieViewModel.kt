package com.example.paginationandroid.presentation.viewModel

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paginationandroid.data.DataSource.MoviePagingSource
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.data.network.ApiService
import com.example.paginationandroid.data.network.ApiClient
import com.example.paginationandroid.data.repositories.MovieRepositoryImpl
import com.google.android.material.textfield.TextInputLayout

class MovieViewModel : ViewModel() {
    var apiClient: ApiClient = ApiService().getRetrofitServiceApi()
    val repo = MovieRepositoryImpl(apiClient = apiClient)
    fun returnMovies(
        errorDisplay: TextInputLayout,
        progressBar: ProgressBar
    ): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {
                MoviePagingSource(
                    errorDisplay,
                    progressBar,
                    repo
                )
            }).liveData.cachedIn(
            viewModelScope
        )
    }
}