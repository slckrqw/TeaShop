package com.example.teashop.data.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductAccounting(
    val id: Long = 1234,
    val title: String = "Chai",
    val orderCount: Int = 52,
    val quantity: Int = 52,
    var active: Boolean = true,
    val imageUrl: String
): Parcelable