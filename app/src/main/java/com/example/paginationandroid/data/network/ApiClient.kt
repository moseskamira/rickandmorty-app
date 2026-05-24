package com.example.paginationandroid.data.network

import com.example.paginationandroid.data.models.EpisodeDto
import com.example.paginationandroid.data.models.MovieDto
import com.example.paginationandroid.data.models.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiClient {
    @GET(Apis.CHARACTERS)
    suspend fun getMovies(@Query("page") query: Int): Response<MovieResponseDto>

    @GET(Apis.SINGLE_CHARACTER)
    suspend fun getSingleMovie(@Path("id") id: Int): Response<MovieDto>

    @GET
    suspend fun getEpisode(
        @Url url: String
    ): Response<EpisodeDto>
}