package com.example.teashop.data.model.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val role: String
)
