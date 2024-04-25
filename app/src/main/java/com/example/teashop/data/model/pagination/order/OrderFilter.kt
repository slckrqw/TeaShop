package com.example.teashop.data.model.pagination.order

import com.example.teashop.data.model.order.OrderStatus
import java.time.ZonedDateTime

data class OrderFilter (
    val byCurrentUser: Boolean = true,
    val minPrice: Double? = 0.0,
    val maxPrice: Double? = 100000.0,
    val dateFrom: ZonedDateTime? = null,
    val dateTo: ZonedDateTime? = null,
    val status: OrderStatus? = null
)
