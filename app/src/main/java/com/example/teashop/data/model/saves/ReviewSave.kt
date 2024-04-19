package com.example.teashop.data.model.saves

data class ReviewSave(
    private val id: Long,
    private val productId: Long,
    private val images: List<Long>,
    private val stars: Short,
    private val reviewText: String
)
