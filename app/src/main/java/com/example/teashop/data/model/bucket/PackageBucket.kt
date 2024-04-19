package com.example.teashop.data.model.bucket

import com.example.teashop.data.model.variant.Variant

data class PackageBucket(
    val packId: Long,
    val variant: Variant,
    val price: Double,
    val product: ProductBucket,
    val quantityInBucket: Int
)
