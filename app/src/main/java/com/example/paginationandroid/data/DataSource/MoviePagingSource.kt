package com.example.paginationandroid.data.DataSource

import android.view.View
import android.widget.ProgressBar
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.domain.repositories.MovieRepository
import com.google.android.material.textfield.TextInputLayout

class MoviePagingSource(
    private val errorDisplay: TextInputLayout,
    private val progressBar: ProgressBar,
    private val repo: MovieRepository
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        progressBar.visibility = View.VISIBLE
        val currentPage = params.key ?: FIRST_PAGE_INDEX
        return try {
            val response = repo.getMovies(currentPage)
            val movieResponse = response.data
            progressBar.visibility = View.GONE
            val movies = movieResponse?.results ?: emptyList()
            LoadResult.Page(
                data = movies,
                prevKey = if (currentPage == FIRST_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (movies.isEmpty()) null else currentPage + 1
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