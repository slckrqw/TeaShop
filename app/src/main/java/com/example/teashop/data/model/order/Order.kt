package com.example.teashop.data.model.order

import android.os.Parcelable
import com.example.teashop.data.model.recipient.Recipient
import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.packages.PackageOrder
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Order(
    val id: Long,
    val createdDate: ZonedDateTime,
    val recipient: Recipient = Recipient(),
    val status: OrderStatus,
    val packageOrders: List<PackageOrder> = listOf(),
    val trackNumber: String?,
    val address: Address = Address(),
    val bonusesSpent: Int,
    val bonusesAccrued: Int,
    val totalCost: Double,
): Parcelable
