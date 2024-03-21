package com.example.teashop.data

data class Feedback(
    val userId: String,
    val rate: Int,
    val content: String,
    val imageResourceId: Int
)
