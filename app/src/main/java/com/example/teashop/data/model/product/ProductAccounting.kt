package com.example.teashop.data.model.product

data class ProductAccounting(
    val id: Long,
    val title: String,
    val orderCount: Int,
    val quantity: Int,
    val active: Boolean
)