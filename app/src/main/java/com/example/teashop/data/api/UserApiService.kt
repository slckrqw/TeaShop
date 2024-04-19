package com.example.teashop.data.api

import com.example.teashop.data.model.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApiService {
    @GET("/users/logged-user")
    suspend fun getLoggedUserInfo(@Header("Authorization") token: String): Response<User>

    companion object {
        val userApiService: UserApiService by lazy {
            retrofitBuilder.create(UserApiService::class.java)
        }
    }
}