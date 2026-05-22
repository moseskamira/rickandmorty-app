package com.example.paginationandroid.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.domain.repositories.MovieRepository

class MoviePagingSource(
    private val repo: MovieRepository
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: FIRST_PAGE_INDEX
            val response = repo.getMovies(currentPage)
            if (response.success) {
                val movies = response.data?.results ?: emptyList()
                LoadResult.Page(
                    data = movies,
                    prevKey = if (currentPage == FIRST_PAGE_INDEX) null else currentPage - 1,
                    nextKey = if (movies.isEmpty()) null else currentPage + 1
                )
            } else {
                LoadResult.Error(
                    Throwable(response.error ?: "Unknown error")
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}