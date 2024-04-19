package com.example.teashop.data.model.packages

import com.example.teashop.data.model.variant.Variant

data class PackageProduct(
    val id: Long,
    val variant: Variant,
    val quantity: Int,
    val price: Double
)
