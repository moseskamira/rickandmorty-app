package com.character.mobile.domain.models

data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)