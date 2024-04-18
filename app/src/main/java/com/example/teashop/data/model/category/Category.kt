package com.example.teashop.data.model.category

import com.example.teashop.data.model.image.ImageDto

data class Category(
    val id: Int,
    val name: String,
    val image: ImageDto
)