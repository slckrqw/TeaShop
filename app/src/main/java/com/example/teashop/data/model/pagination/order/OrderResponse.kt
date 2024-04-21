package com.example.teashop.data.model.pagination.order

import com.example.teashop.data.model.order.OrderShort
import com.example.teashop.data.model.pagination.PageResult

data class OrderResponse (
    val data: List<OrderShort>,
    val pagingCommand: PageResult
)