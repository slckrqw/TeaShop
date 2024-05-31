package com.example.teashop.data.model.saves

import com.example.teashop.data.model.variant.VariantType

data class PackageSave(
    val id: Long? = null,
    val variant: VariantType,
    val quantity: Int,
    val price: Double
)
