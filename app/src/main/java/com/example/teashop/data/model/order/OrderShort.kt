package com.example.teashop.data.model.order

import com.example.teashop.data.model.packages.PackageOrder

data class OrderShort(
    val id: Long,
    val status: OrderStatus,
    val packageOrders: List<PackageOrder>,
    val trackNumber: String,
    val totalCost: Double
)
