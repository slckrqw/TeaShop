package com.example.teashop.data.model.packages

import android.os.Parcelable
import com.example.teashop.data.model.variant.VariantType
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackageShort(
    val id: Long = 1,
    val variantType: VariantType = VariantType.FIFTY_GRAMS,
    val price: Double = 250.0
): Parcelable
