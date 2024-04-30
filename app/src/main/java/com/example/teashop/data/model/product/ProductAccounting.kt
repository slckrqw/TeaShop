package com.example.teashop.data.model.product

import com.example.teashop.data.model.image.Image

data class ProductAccounting(
    val id: Long = 1234,
    val title: String = "Chai",
    val orderCount: Int = 52,
    val quantity: Int = 52,
    val active: Boolean = true,
    val image: Image = Image()
)