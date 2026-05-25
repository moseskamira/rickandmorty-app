package com.example.paginationandroid.domain.repositories


import com.example.paginationandroid.data.network.NetworkResponse
import com.example.paginationandroid.domain.models.Episode
import com.example.paginationandroid.domain.models.Character
import com.example.paginationandroid.domain.models.CharacterResponse

interface CharacterRepository {
    suspend fun getCharacters(page: Int): NetworkResponse<CharacterResponse>
    suspend fun getSingleMovie(id: Int): NetworkResponse<Character>
    suspend fun getCharacterEpisodes(urls: List<String>): NetworkResponse<List<Episode>>
    suspend fun getEpisodeCharacters(urls: List<String>): NetworkResponse<List<Character>>
}