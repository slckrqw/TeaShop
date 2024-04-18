package com.example.teashop.data.model.order

import android.os.Parcelable
import com.example.teashop.data.model.product.Product
import com.example.teashop.data.model.recipient.Recipient
import com.example.teashop.data.model.address.Address
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