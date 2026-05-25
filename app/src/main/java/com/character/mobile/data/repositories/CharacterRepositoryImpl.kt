package com.character.mobile.data.repositories

import com.character.mobile.data.mappers.toDomain
import com.character.mobile.data.network.ApiClient
import com.character.mobile.data.network.NetworkResponse
import com.character.mobile.domain.models.Character
import com.character.mobile.domain.models.CharacterResponse
import com.character.mobile.domain.models.Episode
import com.character.mobile.domain.repositories.CharacterRepository

class CharacterRepositoryImpl(private val apiClient: ApiClient) :
    CharacterRepository {
    override suspend fun getCharacters(page: Int): NetworkResponse<CharacterResponse> {
        return try {
            val response = apiClient.getMovies(page)
            if (response.isSuccessful) {
                val domain = response.body()?.toDomain()
                NetworkResponse(
                    success = true,
                    data = domain
                )
            } else {
                NetworkResponse(
                    success = false,
                    error = response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            NetworkResponse(
                success = false,
                error = e.localizedMessage ?: "Unknown error"
            )
        }
    }

    override suspend fun getSingleMovie(id: Int): NetworkResponse<Character> {
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

    override suspend fun getCharacterEpisodes(
        urls: List<String>
    ): NetworkResponse<List<Episode>> {

        return try {
            val episodes = mutableListOf<Episode>()
            for (url in urls) {
                try {
                    val response = apiClient.getEpisode(url = url)
                    if (!response.isSuccessful) {
                        continue
                    }
                    val body = response.body()
                    if (body != null) {
                        episodes.add(body.toDomain())
                    }
                } catch (e: Exception) {
                    continue
                }
            }

            NetworkResponse(
                success = true,
                data = episodes
            )

        } catch (e: Exception) {
            NetworkResponse(
                success = false,
                error = e.message
            )
        }
    }

    override suspend fun getEpisodeCharacters(
        urls: List<String>
    ): NetworkResponse<List<Character>> {
        return try {
            val characters = mutableListOf<Character>()
            for (url in urls) {
                try {
                    val response = apiClient.getCharacter(url)
                    if (!response.isSuccessful) {
                        continue
                    }
                    val body = response.body()
                    if (body != null) {
                        characters.add(body.toDomain())
                    }
                } catch (e: Exception) {
                    continue
                }
            }
            NetworkResponse(
                success = true,
                data = characters
            )
        } catch (e: Exception) {
            NetworkResponse(
                success = false,
                error = e.message ?: "Unknown error occurred"
            )
        }
    }
}