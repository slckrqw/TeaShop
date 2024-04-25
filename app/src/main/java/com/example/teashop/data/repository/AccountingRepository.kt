package com.example.teashop.data.repository

import com.example.teashop.data.api.AccountingApiService.Companion.accountingApiService
import okhttp3.MultipartBody
import retrofit2.Response

class AccountingRepository {
    suspend fun uploadImages(
        files: List<MultipartBody.Part>
    ): Response<List<Long?>> {
        return accountingApiService.uploadImages(files)
    }
}