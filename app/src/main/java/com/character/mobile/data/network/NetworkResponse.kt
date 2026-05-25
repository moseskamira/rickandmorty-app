package com.character.mobile.data.network

data class NetworkResponse<T>(
    val success: Boolean = false,
    val data: T? = null,
    val error: String? = null,
    val extra: String? = null
)
