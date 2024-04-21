package com.example.teashop.data.model.pagination.order

import com.example.teashop.data.model.order.OrderStatus
import java.time.ZonedDateTime

data class OrderFilter (
    val byCurrentUser: Boolean,
    val minPrice: Double?,
    val maxPrice: Double?,
    val dateFrom: ZonedDateTime?,
    val dateTo: ZonedDateTime?,
    val status: OrderStatus?
)
