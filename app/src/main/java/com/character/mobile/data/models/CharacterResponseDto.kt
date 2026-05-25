package com.character.mobile.data.models

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)