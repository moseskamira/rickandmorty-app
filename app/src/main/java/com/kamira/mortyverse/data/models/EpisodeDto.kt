package com.kamira.mortyverse.data.models

import com.google.gson.annotations.SerializedName


data class EpisodeDto(
    val id: Long,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
)