package com.example.teashop.data.model.order

import com.example.teashop.data.model.packages.PackageOrder
import java.time.ZonedDateTime

data class OrderShort(
    val id: Long,
    val status: OrderStatus,
    val createdDate: ZonedDateTime,
    val packageOrders: List<PackageOrder>,
    val trackNumber: String?,
    val totalCost: Double
)
