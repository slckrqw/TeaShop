package com.example.teashop.data.api

import com.example.teashop.data.model.auth.AuthRequest
import com.example.teashop.data.model.auth.AuthResponse
import com.example.teashop.data.model.auth.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService{
    @POST("/auth/authenticate")
    suspend fun authenticate(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("/auth/registration")
    suspend fun registration(@Body registrationRequest: RegistrationRequest): Response<String>

    companion object {
        val authApiService: AuthApiService by lazy {
            retrofitBuilder.create(AuthApiService::class.java)
        }
    }
}