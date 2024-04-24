package com.example.teashop.data.model.pagination.review

data class ReviewFilter(
    var byCurrentUser: Boolean = false,
    var productId: Long? = null
)
