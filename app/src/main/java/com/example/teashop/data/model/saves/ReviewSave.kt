package com.example.teashop.data.model.saves

data class ReviewSave(
    val id: Long? = null,
    val productId: Long,
    var images: List<Long?>?,
    val stars: Short,
    val reviewText: String
)
