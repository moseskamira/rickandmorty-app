package com.kamira.mortyverse.domain.models

data class CharacterResponse(
    val info: Info,
    val results: List<Character>
)