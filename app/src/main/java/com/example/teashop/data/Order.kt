package com.example.teashop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: Long,
    val recipient: Recipient = Recipient(),
    val status: OrderStatus,
    val productList: List<Product>,
    val trackNumber: String,
    val address: Address = Address(),
    val bonusesSpent: Int,
    val bonusesAccrued: Int,
    val totalCost: Double,
):Parcelable
