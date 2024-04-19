package com.example.teashop.data.model.packages

import com.example.teashop.data.model.variant.Variant

data class PackageProduct(
    private val id: Long,
    private val variant: Variant,
    private val quantity: Int,
    private val price: Double
)
