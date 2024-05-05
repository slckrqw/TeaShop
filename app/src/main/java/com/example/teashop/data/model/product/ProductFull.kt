package com.example.teashop.data.model.product

import android.os.Parcelable
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.image.Image
import com.example.teashop.data.model.packages.PackageProduct
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class ProductFull(
    val id: Long = 0,
    val packages: MutableList<PackageProduct> = mutableListOf(),
    var article:String = "",
    var title: String = "",
    var description: String = "",
    var discount: Int = 0,
    val countOfReviews: Int = 0,
    val averageRating: Double = 0.0,
    val createdTime: ZonedDateTime = ZonedDateTime.now(),
    var category: Category = Category(),
    var images: List<Image> = listOf(),
): Parcelable
