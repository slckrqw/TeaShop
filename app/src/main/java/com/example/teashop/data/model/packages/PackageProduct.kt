package com.example.teashop.data.model.packages

import android.os.Parcelable
import com.example.teashop.data.model.variant.Variant
import com.example.teashop.data.model.variant.VariantType
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackageProduct(
    val id: Long = 1234,
    val variant: VariantType = VariantType.FIVE_HUNDRED_GRAMS,
    val quantity: Int = 1,
    val price: Double = 250.0
): Parcelable
