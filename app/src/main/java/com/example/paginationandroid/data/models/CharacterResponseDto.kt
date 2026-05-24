package com.example.paginationandroid.data.models

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)