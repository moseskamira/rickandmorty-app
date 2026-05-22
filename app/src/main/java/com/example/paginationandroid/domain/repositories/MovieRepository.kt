package com.example.paginationandroid.domain.repositories

import com.example.paginationandroid.data.network.NetworkResponse
import com.example.paginationandroid.domain.models.MovieResponse

interface MovieRepository {
    suspend fun getMovies(page: Int): NetworkResponse<MovieResponse>
}