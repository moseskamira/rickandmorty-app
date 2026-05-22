package com.example.paginationandroid.data.mappers

import com.example.paginationandroid.data.models.InfoDto
import com.example.paginationandroid.data.models.MovieDto
import com.example.paginationandroid.data.models.MovieResponseDto
import com.example.paginationandroid.domain.models.Info
import com.example.paginationandroid.domain.models.Movie
import com.example.paginationandroid.domain.models.MovieResponse

fun MovieDto.toDomain(): Movie {
    return Movie(
        name = name,
        species = species,
        image = image
    )
}

fun InfoDto.toDomain(): Info {
    return Info(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}

fun MovieResponseDto.toDomain(): MovieResponse {
    return MovieResponse(
        info = info.toDomain(),
        results = results.map { dto -> dto.toDomain() }
    )
}