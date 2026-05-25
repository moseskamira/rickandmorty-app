package com.kamira.mortyverse.domain.repositories


import com.kamira.mortyverse.data.network.NetworkResponse
import com.kamira.mortyverse.domain.models.Episode
import com.kamira.mortyverse.domain.models.Character
import com.kamira.mortyverse.domain.models.CharacterResponse

interface CharacterRepository {
    suspend fun getCharacters(page: Int): NetworkResponse<CharacterResponse>
    suspend fun getSingleMovie(id: Int): NetworkResponse<Character>
    suspend fun getCharacterEpisodes(urls: List<String>): NetworkResponse<List<Episode>>
    suspend fun getEpisodeCharacters(urls: List<String>): NetworkResponse<List<Character>>
}