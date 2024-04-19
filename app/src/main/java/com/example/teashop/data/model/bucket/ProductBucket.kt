package com.example.teashop.data.model.bucket

import com.example.teashop.data.model.image.Image


data class ProductBucket(
    val id: Long,
    val title: String,
    val discount: Short,
    val images: List<Image>
)
