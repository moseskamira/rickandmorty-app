package com.kamira.mortyverse.data.models

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)