package com.example.paginationandroid.viewModel

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paginationandroid.DataSource.MoviePagingSource
import com.example.paginationandroid.model.Movie
import com.example.paginationandroid.network.RetrofitInstance
import com.example.paginationandroid.network.RetrofitServiceAPI
import com.google.android.material.textfield.TextInputLayout

class MovieViewModel : ViewModel() {
    var serviceApi: RetrofitServiceAPI = RetrofitInstance().getRetrofitServiceApi()
    fun returnMovies(
        errorDisplay: TextInputLayout,
        progressBar: ProgressBar
    ): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 100),
            pagingSourceFactory = {
                MoviePagingSource(
                    serviceApi,
                    errorDisplay,
                    progressBar
                )
            }).liveData.cachedIn(
            viewModelScope
        )
    }
}