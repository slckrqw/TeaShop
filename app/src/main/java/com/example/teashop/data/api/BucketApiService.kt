package com.example.teashop.data.api

import com.example.teashop.data.model.bucket.Bucket
import com.example.teashop.data.model.saves.ProductToBucket
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BucketApiService {
    @GET("/bucket")
    suspend fun getBucket(
        @Header("Authorization") token: String,
    ): Response<Bucket>

    @POST("/bucket")
    suspend fun addProductToBucket(
        @Header("Authorization") token: String,
        @Body request: ProductToBucket
    ): Response<Bucket>

    @DELETE("/bucket/{packId}")
    suspend fun deletePack(
        @Header("Authorization") token: String,
        @Path("packId") packId: Long
    ): Response<Bucket>

    @DELETE("bucket/clear")
    suspend fun clearBucket(
        @Header("Authorization") token: String
    ): Response<Bucket>

    companion object {
        val bucketApiService: BucketApiService by lazy {
            retrofitBuilder.create(BucketApiService::class.java)
        }
    }
}