package com.example.teashop.data.model.auth

data class RegistrationRequest(
    val name: String,
    val email: String,
    val password: String
)