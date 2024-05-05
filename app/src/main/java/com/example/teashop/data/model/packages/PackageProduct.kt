package com.example.teashop.data.model.packages

import android.os.Parcelable
import com.example.teashop.data.model.variant.Variant
import com.example.teashop.data.model.variant.VariantType
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackageProduct(
    val id: Long = 0,
    var variant: Variant = Variant(1, VariantType.PACK),
    var quantity: Int = 0,
    var price: Double = 0.0
): Parcelable
