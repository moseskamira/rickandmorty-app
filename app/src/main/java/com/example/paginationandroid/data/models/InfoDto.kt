package com.example.paginationandroid.data.models

data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)