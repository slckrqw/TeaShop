package com.example.teashop.data.repository

import com.example.teashop.data.api.ReviewApiService.Companion.reviewApiService
import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.pagination.review.ReviewResponse
import com.example.teashop.data.model.review.Review
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
}