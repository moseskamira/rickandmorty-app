package com.example.paginationandroid.data.repositories

import com.example.paginationandroid.data.mappers.toDomain
import com.example.paginationandroid.data.network.ApiClient
import com.example.paginationandroid.data.network.NetworkResponse
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.domain.models.MovieResponse
import com.example.paginationandroid.domain.repositories.MovieRepository

class MovieRepositoryImpl(private val apiClient: ApiClient) :
    MovieRepository {
    override suspend fun getMovies(page: Int): NetworkResponse<MovieResponse> {
        try {
            val response = apiClient.getMovies(page)
            if (response.isSuccessful) {
                val dtoResponse = response.body()
                val domainResponse = dtoResponse?.toDomain()
                return NetworkResponse(success = true, data = domainResponse)
            } else {
                val error = response.errorBody()?.string()
                return NetworkResponse(success = false, error = error)
            }
        } catch (e: Exception) {
            val error = e.message
            return NetworkResponse(success = false, error = error)

        }

    }

    override suspend fun getSingleMovie(id: Int): NetworkResponse<Movie> {
        try {
            val response = apiClient.getSingleMovie(id)
            if (response.isSuccessful) {
                val dtoMovie = response.body()
                val domainMovie = dtoMovie?.toDomain()
                return NetworkResponse(success = true, data = domainMovie)
            } else {
                val error = response.errorBody()?.string()
                return NetworkResponse(success = false, error = error)
            }
        } catch (e: Exception) {
            val error = e.message
            return NetworkResponse(success = false, error = error)
        }
    }
}