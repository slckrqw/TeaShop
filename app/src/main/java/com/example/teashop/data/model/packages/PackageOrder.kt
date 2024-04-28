package com.example.teashop.data.model.packages

import android.os.Parcelable
import com.example.teashop.data.model.variant.VariantType
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackageOrder(
    val imageUrl: String,
    val productTitle: String,
    val price: Double,
    val type: VariantType,
    val quantity: Int
): Parcelable
