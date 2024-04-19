package com.example.teashop.data.model.bucket

import com.example.teashop.data.model.imageDto.ImageDto

data class ProductBucket(
    private val id: Long,
    private val title: String,
    private val discount: Short,
    private val images: List<ImageDto>
)
