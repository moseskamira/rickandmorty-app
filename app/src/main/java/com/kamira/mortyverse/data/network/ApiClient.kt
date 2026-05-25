package com.kamira.mortyverse.data.network

import com.kamira.mortyverse.data.models.EpisodeDto
import com.kamira.mortyverse.data.models.CharacterDto
import com.kamira.mortyverse.data.models.CharacterResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiClient {
    @GET(Apis.CHARACTERS)
    suspend fun getMovies(@Query("page") query: Int): Response<CharacterResponseDto>

    @GET(Apis.SINGLE_CHARACTER)
    suspend fun getSingleMovie(@Path("id") id: Int): Response<CharacterDto>

    @GET
    suspend fun getEpisode(
        @Url url: String
    ): Response<EpisodeDto>

    @GET
    suspend fun getCharacter(
        @Url url: String
    ): Response<CharacterDto>
}