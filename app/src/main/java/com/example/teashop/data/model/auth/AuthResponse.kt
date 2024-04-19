package com.example.teashop.data.model.auth

data class AuthResponse(
    val token: String,
    val refreshToken: String,
    val role: String
)
