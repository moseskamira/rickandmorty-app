package com.character.mobile.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.character.mobile.domain.models.Character
import com.character.mobile.domain.repositories.CharacterRepository

class MoviePagingSource(
    private val repo: CharacterRepository
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Character> {
        return try {
            val currentPage = params.key ?: FIRST_PAGE_INDEX
            val response = repo.getCharacters(currentPage)
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