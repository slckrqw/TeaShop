package com.example.teashop.data.model.product

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.teashop.R
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.image.Image
import com.example.teashop.data.model.packages.PackageProduct
import com.example.teashop.data.model.packages.PackageShort
import com.example.teashop.data.model.review.Review
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime
@Parcelize
data class ProductFull(
    val id: Long = 1234,
    val packages: List<PackageProduct> = listOf(PackageProduct()),
    val article:String = "1234",
    val title: String = "Зелёный чай",
    val description: String = "скуф",
    val discount: Double = 5.0,
    val countOfReviews: Int = 6,
    val averageRating: Double = 4.9,
    val createdTime: ZonedDateTime = ZonedDateTime.now(),
    val category: Category = Category(),
    val images: List<Image> = listOf(Image()),
): Parcelable
