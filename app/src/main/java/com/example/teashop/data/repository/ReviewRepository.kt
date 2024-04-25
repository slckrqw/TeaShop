package com.example.teashop.data.repository

import com.example.teashop.data.api.ReviewApiService.Companion.reviewApiService
import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.pagination.review.ReviewResponse
import com.example.teashop.data.model.review.Review
import com.example.teashop.data.model.saves.ReviewSave
import retrofit2.Response

class ReviewRepository {
    suspend fun getAllReviewByProduct(
        token: String?,
        reviewPagingRequest: ReviewPagingRequest)
    : Response<ReviewResponse> {
        return reviewApiService.getAllReviewByProduct(token, reviewPagingRequest)
    }

    suspend fun existsReview(
        token: String,
        productId: Long
    ): Response<Review?> {
        return reviewApiService.existsReview(token, productId)
    }

    suspend fun saveReview(
        token: String,
        reviewSave: ReviewSave
    ): Response<Review> {
        return reviewApiService.saveReview(token, reviewSave)
    }

    suspend fun updateReview(
        token: String,
        reviewSave: ReviewSave
    ): Response<Review> {
        return reviewApiService.updateReview(token, reviewSave)
    }

    suspend fun deleteReview(
        token: String,
        id: Long
    ): Response<String> {
        return reviewApiService.deleteReview(token, id)
    }
}