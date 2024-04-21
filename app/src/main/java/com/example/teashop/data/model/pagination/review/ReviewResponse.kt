package com.example.teashop.data.model.pagination.review

import com.example.teashop.data.model.pagination.PageResult
import com.example.teashop.data.model.review.Review

data class ReviewResponse (
    val data: List<Review>,
    val pagingCommand: PageResult
)