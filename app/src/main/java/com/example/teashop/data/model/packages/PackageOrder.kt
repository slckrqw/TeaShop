package com.example.teashop.data.model.packages

data class PackageOrder(
    val imageUrl: String,
    val productTitle: String,
    val price: Double,
    val type: String,
    val quantity: Int
)
