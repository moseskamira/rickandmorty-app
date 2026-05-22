package com.example.paginationandroid.data.network

data class NetworkResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: String?,
    val extra: String?
)
