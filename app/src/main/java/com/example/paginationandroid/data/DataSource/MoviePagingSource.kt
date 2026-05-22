package com.example.paginationandroid.data.DataSource

import android.view.View
import android.widget.ProgressBar
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.data.network.ApiClient
import com.google.android.material.textfield.TextInputLayout

class MoviePagingSource(
    private val retrofitServiceAPI: ApiClient,
    private val errorDisplay: TextInputLayout,
    private val progressBar: ProgressBar
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        progressBar.visibility = View.VISIBLE
        val currentPage = params.key ?: FIRST_PAGE_INDEX
        return try {
            val response = retrofitServiceAPI.getDataFromApi(currentPage)
            progressBar.visibility = View.GONE
            val moviesList = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = moviesList,
                prevKey = if (currentPage == FIRST_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (moviesList.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            errorDisplay.error = e.message
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}