package com.example.teashop.data.api

import com.example.teashop.data.model.auth.AuthRequest
import com.example.teashop.data.model.auth.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService{
    @POST("/auth/authenticate")
    suspend fun aunthenticate(@Body authRequest: AuthRequest): Call<AuthResponse>

    companion object {
        val authApiService: AuthApiService by lazy {
            retrofitBuilder.create(AuthApiService::class.java)
        }
    }
}