package com.example.teashop.data.repository

import com.example.teashop.data.api.AuthApiService.Companion.authApiService
import com.example.teashop.data.model.auth.AuthRequest
import com.example.teashop.data.model.auth.AuthResponse
import retrofit2.Call

class AuthRepository {
    suspend fun authenticate(authRequest: AuthRequest): Call<AuthResponse>{
        return authApiService.aunthenticate(authRequest)
    }
}