package com.example.paginationandroid.domain.models

data class MovieResponse(
    val info: Info,
    val results: List<Movie>
)