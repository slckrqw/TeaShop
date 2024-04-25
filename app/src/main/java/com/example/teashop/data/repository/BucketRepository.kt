package com.example.teashop.data.repository

import com.example.teashop.data.api.BucketApiService.Companion.bucketApiService
import com.example.teashop.data.model.bucket.Bucket
import com.example.teashop.data.model.saves.ProductToBucket
import retrofit2.Response

class BucketRepository {
    suspend fun getBucket(token: String): Response<Bucket> {
        return bucketApiService.getBucket(token)
    }

    suspend fun addProductToBucket(token: String, request: ProductToBucket): Response<Bucket> {
        return bucketApiService.addProductToBucket(token, request)
    }

    suspend fun deletePack(token: String, packId: Long): Response<Bucket> {
        return bucketApiService.deletePack(token, packId)
    }

    suspend fun clearBucket(token: String): Response<Bucket> {
        return bucketApiService.clearBucket(token)
    }
}