package com.example.teashop.data.api

import com.example.teashop.data.model.order.Order
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountingApiService {
    @Multipart
    @POST("accounting/upload-images")
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): Response<List<Long?>>

    @PUT("accounting/orders/{id}")
    suspend fun updateStatusOrTrack(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Query("status") status: String,
        @Query("track") track: String?
    ): Response<Order>

    companion object {
        val accountingApiService: AccountingApiService by lazy {
            retrofitBuilder.create(AccountingApiService::class.java)
        }
    }
}