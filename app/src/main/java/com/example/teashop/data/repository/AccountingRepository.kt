package com.example.teashop.data.repository

import com.example.teashop.data.api.AccountingApiService.Companion.accountingApiService
import com.example.teashop.data.model.order.Order
import okhttp3.MultipartBody
import retrofit2.Response

class AccountingRepository {
    suspend fun uploadImages(
        files: List<MultipartBody.Part>
    ): Response<List<Long?>> {
        return accountingApiService.uploadImages(files)
    }

    suspend fun updateStatusOrTrack(
        token: String,
        id: Long,
        status: String,
        track: String?
    ): Response<Order> {
        return accountingApiService.updateStatusOrTrack(token, id, status, track)
    }
}