package com.example.teashop.data.model.product

import android.os.Parcelable
import com.example.teashop.data.model.image.Image
import com.example.teashop.data.model.packages.PackageShort
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductShort(
    val id: Long = 1234,
    val article:String = "1234",
    val isFavorite: Boolean = false,
    val packages: List<PackageShort> = listOf(PackageShort()),
    val title: String = "Зелёный чай",
    val discount: Int = 5,
    val countOfReviews: Int = 6,
    val averageRating: Double = 4.9,
    val images: List<Image> = listOf(Image())
): Parcelable
