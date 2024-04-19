package com.example.teashop.data.model.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    val email: String,
    val password: String
)
