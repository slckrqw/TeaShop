package com.example.teashop.data.model.bucket

import android.os.Parcelable
import com.example.teashop.data.model.variant.Variant
import kotlinx.parcelize.Parcelize

@Parcelize
data class PackageBucket(
    val packId: Long,
    val variant: Variant,
    val price: Double,
    val product: ProductBucket,
    val quantityInBucket: Int
): Parcelable
