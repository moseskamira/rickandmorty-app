package com.example.paginationandroid.data.models

data class CharacterDto(
    val id: Int?,
    val name: String?,
    val species: String?,
    val image: String?,
    val status: String?,
    val type: String?,
    val gender: String?,
    val origin: OriginDto?,
    val location: OriginDto?,
    val episode: List<String>?,
    val url: String?,
    val created: String?
)