package com.example.teashop.data.model.product

import android.os.Parcelable
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.image.Image
import com.example.teashop.data.model.packages.PackageProduct
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class ProductFull(
    val id: Long = 1,
    val packages: List<PackageProduct> = listOf(PackageProduct()),
    val article:String = "1234",
    val title: String = "Зелёный чай",
    val description: String = "",
    val discount: Int = 5,
    val countOfReviews: Int = 6,
    val averageRating: Double = 4.9,
    val createdTime: ZonedDateTime = ZonedDateTime.now(),
    val category: Category = Category(),
    val images: List<Image> = listOf(Image()),
): Parcelable
