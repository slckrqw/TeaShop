package com.example.teashop.data.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductAccounting(
    val id: Long = 0,
    val title: String = "",
    val orderCount: Int = 0,
    val quantity: Int = 0,
    var active: Boolean = true,
    val imageUrl: String
): Parcelable