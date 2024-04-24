package com.example.teashop.data.api

import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.pagination.review.ReviewResponse
import com.example.teashop.data.model.review.Review
import com.example.teashop.data.model.saves.ReviewSave
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApiService {
    @POST("/review/actions/search-by-filter")
    suspend fun getAllReviewByProduct(
        @Header("Authorization") token: String?,
        @Body reviewPagingRequest: ReviewPagingRequest
    ): Response<ReviewResponse>

    @GET("/review")
    suspend fun existsReview(
        @Header("Authorization") token: String,
        @Query("productId") productId: Long
    ): Response<Review?>

    @POST("/review")
    suspend fun saveReview(
        @Header("Authorization") token: String,
        @Body reviewSave: ReviewSave
    ): Response<Review>

    @PUT("/review")
    suspend fun updateReview(
        @Header("Authorization") token: String,
        @Body reviewSave: ReviewSave
    ): Response<Review>

    @DELETE("/review/{id}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<String>

    companion object {
        val reviewApiService: ReviewApiService by lazy {
            retrofitBuilder.create(ReviewApiService::class.java)
        }
    }
}