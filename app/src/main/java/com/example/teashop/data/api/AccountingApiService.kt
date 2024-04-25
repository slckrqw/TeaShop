package com.example.teashop.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AccountingApiService {
    @Multipart
    @POST("accounting/upload-images")
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): Response<List<Long?>>

    companion object {
        val accountingApiService: AccountingApiService by lazy {
            retrofitBuilder.create(AccountingApiService::class.java)
        }
    }
}