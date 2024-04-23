package com.example.teashop.data.model.order

import android.os.Parcelable
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.recipient.Recipient
import com.example.teashop.data.model.address.Address
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Order(
    val id: Long,
    val createdDate: ZonedDateTime,
    val recipient: Recipient = Recipient(),
    val status: OrderStatus,
    val productList: List<ProductFull> = listOf(),
    val trackNumber: String,
    val address: Address = Address(),
    val bonusesSpent: Int,
    val bonusesAccrued: Int,
    val totalCost: Double,
): Parcelable
