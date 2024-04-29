package com.example.teashop.data.model.pagination.order

import com.example.teashop.data.model.order.OrderStatus
import java.time.ZonedDateTime

data class OrderFilter (
    var byCurrentUser: Boolean = true,
    var minPrice: Double? = 0.0,
    var maxPrice: Double? = null,
    var dateFrom: ZonedDateTime? = null,
    val dateTo: ZonedDateTime? = null,
    var status: OrderStatus? = null
)
