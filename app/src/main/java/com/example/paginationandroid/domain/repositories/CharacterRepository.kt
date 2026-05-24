package com.example.paginationandroid.domain.repositories


import com.example.paginationandroid.data.network.NetworkResponse
import com.example.paginationandroid.domain.models.Episode
import com.example.paginationandroid.domain.models.Character
import com.example.paginationandroid.domain.models.CharacterResponse

interface CharacterRepository {
    suspend fun getMovies(page: Int): NetworkResponse<CharacterResponse>
    suspend fun getSingleMovie(id: Int): NetworkResponse<Character>
    suspend fun getSingleEpisode(url: String): NetworkResponse<Episode>
    suspend fun getCharacters(urls: List<String>): NetworkResponse<List<Character>>
}