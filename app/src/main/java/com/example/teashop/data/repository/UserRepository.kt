package com.example.teashop.data.repository

import com.example.teashop.data.api.UserApiService.Companion.userApiService
import com.example.teashop.data.model.saves.UserSave
import com.example.teashop.data.model.user.User
import retrofit2.Response

class UserRepository {
    suspend fun getLoggedUserInfo(token: String): Response<User> {
        return userApiService.getLoggedUserInfo(token)
    }

    suspend fun saveUserInfo(token: String, userSave: UserSave): Response<User> {
        return userApiService.saveUserInfo(token, userSave)
    }

    suspend fun deleteAccount(token: String): Response<String?> {
        return userApiService.deleteAccount(token)
    }
}