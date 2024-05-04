package com.example.teashop.data.model.product

import android.os.Parcelable
import com.example.teashop.data.model.image.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductAccounting(
    val id: Long = 1234,
    val title: String = "Chai",
    val orderCount: Int = 52,
    val quantity: Int = 52,
    var active: Boolean = true,
    val image: Image = Image()
): Parcelable