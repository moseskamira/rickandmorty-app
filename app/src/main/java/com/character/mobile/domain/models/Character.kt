package com.character.mobile.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
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

): Parcelable