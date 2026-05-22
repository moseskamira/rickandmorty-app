package com.example.paginationandroid.data.models

data class MovieResponseDto(
    val info: InfoDto,
    val results: List<MovieDto>
)