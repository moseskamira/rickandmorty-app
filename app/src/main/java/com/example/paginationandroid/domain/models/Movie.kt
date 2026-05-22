package com.example.paginationandroid.domain.models

import com.example.paginationandroid.domain.Origin

data class Movie(
    val id: Int?,
    val name: String?,
    val species: String?,
    val image: String?,
    val status: String?,
    val type: String?,
    val gender: String?,
    val origin: Origin?,
    val location: Origin?,
    val episode: List<String>?,
    val url: String?,
    val created: String?

)