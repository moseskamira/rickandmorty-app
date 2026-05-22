package com.example.paginationandroid.data.network

import com.example.paginationandroid.data.models.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET(Apis.CHARACTERS)
    suspend fun getDataFromApi(@Query("page") query: Int): Response<MovieResponseDto>
}