package com.example.teashop.data.model.bucket

import com.example.teashop.data.model.variant.Variant

data class PackageBucket(
    private val packId: Long,
    private val variant: Variant,
    private val price: Double,
    private val product: ProductBucket,
    private val quantityInBucket: Int
)
