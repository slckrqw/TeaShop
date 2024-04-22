package com.example.teashop.data.api

import com.example.teashop.data.model.bucket.Bucket
import com.example.teashop.data.model.saves.ProductToBucket
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BucketApiService {
    @POST("/bucket")
    suspend fun addProductToBucket(
        @Header("Authorization") token: String,
        @Body request: ProductToBucket
    ): Response<Bucket>

    @GET("/bucket")
    suspend fun getBucket(
        @Header("Authorization") token: String,
    ): Response<Bucket>

    companion object {
        val bucketApiService: BucketApiService by lazy {
            retrofitBuilder.create(BucketApiService::class.java)
        }
    }
}