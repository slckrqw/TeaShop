package com.example.teashop.data.api

import com.example.teashop.data.model.saves.UserSave
import com.example.teashop.data.model.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserApiService {
    @GET("/users/logged-user")
    suspend fun getLoggedUserInfo(@Header("Authorization") token: String): Response<User>
    @PUT("/users")
    suspend fun saveUserInfo(@Header("Authorization") token: String, @Body userSave: UserSave): Response<User>
    @DELETE("/users")
    suspend fun deleteAccount(@Header("Authorization") token: String): Response<String?>

    companion object {
        val userApiService: UserApiService by lazy {
            retrofitBuilder.create(UserApiService::class.java)
        }
    }
}