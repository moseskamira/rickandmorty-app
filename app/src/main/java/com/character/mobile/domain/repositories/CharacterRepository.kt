package com.character.mobile.domain.repositories


import com.character.mobile.data.network.NetworkResponse
import com.character.mobile.domain.models.Episode
import com.character.mobile.domain.models.Character
import com.character.mobile.domain.models.CharacterResponse

interface CharacterRepository {
    suspend fun getCharacters(page: Int): NetworkResponse<CharacterResponse>
    suspend fun getSingleMovie(id: Int): NetworkResponse<Character>
    suspend fun getCharacterEpisodes(urls: List<String>): NetworkResponse<List<Episode>>
    suspend fun getEpisodeCharacters(urls: List<String>): NetworkResponse<List<Character>>
}