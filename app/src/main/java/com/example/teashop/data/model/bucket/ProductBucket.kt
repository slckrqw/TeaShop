package com.example.teashop.data.model.bucket

import android.os.Parcelable
import com.example.teashop.data.model.image.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductBucket(
    val id: Long,
    val title: String,
    val discount: Short,
    val images: List<Image>
): Parcelable
