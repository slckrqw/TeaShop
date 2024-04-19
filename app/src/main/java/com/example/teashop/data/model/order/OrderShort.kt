package com.example.teashop.data.model.order

import com.example.teashop.data.model.packages.PackageOrder

data class OrderShort(
    private val id: Long,
    private val status: OrderStatus,
    private val packageOrders: List<PackageOrder>,
    private val trackNumber: String,
    private val totalCost: Double
)
