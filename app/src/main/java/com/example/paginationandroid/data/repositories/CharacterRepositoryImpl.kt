package com.example.paginationandroid.data.repositories

import com.example.paginationandroid.data.mappers.toDomain
import com.example.paginationandroid.data.network.ApiClient
import com.example.paginationandroid.data.network.NetworkResponse
import com.example.paginationandroid.domain.models.Episode
import com.example.paginationandroid.domain.models.Character
import com.example.paginationandroid.domain.models.CharacterResponse
import com.example.paginationandroid.domain.repositories.CharacterRepository

class CharacterRepositoryImpl(private val apiClient: ApiClient) :
    CharacterRepository {
    override suspend fun getMovies(page: Int): NetworkResponse<CharacterResponse> {
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

    override suspend fun getSingleEpisode(url: String): NetworkResponse<Episode> {
        try {
            val response = apiClient.getEpisode(url = url)
            if (response.isSuccessful) {
                val dtoEpisode = response.body()
                val domainEpisode = dtoEpisode?.toDomain()
                return NetworkResponse(success = true, data = domainEpisode)
            } else {
                val error = response.errorBody()?.string()
                return NetworkResponse(success = false, error = error)
            }
        } catch (e: Exception) {
            val error = e.message
            return NetworkResponse(success = false, error = error)
        }
    }

    override suspend fun getCharacters(
        urls: List<String>
    ): NetworkResponse<List<Character>> {
        return try {
            val characters = mutableListOf<Character>()
            urls.forEach { url ->
                val response = apiClient.getCharacter(url)
                if (!response.isSuccessful) {
                    return NetworkResponse(
                        success = false,
                        error = response.errorBody()?.string()
                            ?: "Failed to fetch character"
                    )
                }
                val body = response.body()
                    ?: return NetworkResponse(
                        success = false,
                        error = "Character response body is null"
                    )
                characters.add(body.toDomain())
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