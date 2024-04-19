package com.example.teashop.data.repository

import com.example.teashop.data.api.AuthApiService.Companion.authApiService
import com.example.teashop.data.model.auth.AuthRequest
import com.example.teashop.data.model.auth.AuthResponse
import com.example.teashop.data.model.auth.RegistrationRequest
import retrofit2.Response

class AuthRepository {
    suspend fun authenticate(authRequest: AuthRequest): Response<AuthResponse> {
        return authApiService.authenticate(authRequest)
    }

    suspend fun registration(registrationRequest: RegistrationRequest): Response<String> {
        return authApiService.registration(registrationRequest)
    }
}