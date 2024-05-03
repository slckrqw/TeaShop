package com.example.teashop.data.model.order

import android.os.Parcelable
import com.example.teashop.data.model.recipient.Recipient
import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.packages.PackageOrder
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Order(
    val id: Long = 123,
    val createdDate: ZonedDateTime = ZonedDateTime.now(),
    val recipient: Recipient,
    var status: OrderStatus = OrderStatus.NEW,
    val packageOrders: List<PackageOrder> = listOf(),
    var trackNumber: String?,
    val address: Address = Address(),
    val bonusesSpent: Int,
    val bonusesAccrued: Int,
    val totalCost: Double,
): Parcelable
