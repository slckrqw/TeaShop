package com.example.teashop.data.model.saves

data class ReviewSave(
    val id: Long,
    val productId: Long,
    val images: List<Long>,
    val stars: Short,
    val reviewText: String
)
