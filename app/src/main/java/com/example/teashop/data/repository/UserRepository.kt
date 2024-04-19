package com.example.teashop.data.repository

import com.example.teashop.data.api.UserApiService.Companion.userApiService
import com.example.teashop.data.model.user.User
import retrofit2.Response

class UserRepository {
    suspend fun getLoggedUserInfo(token: String): Response<User> {
        return userApiService.getLoggedUserInfo(token)
    }
}