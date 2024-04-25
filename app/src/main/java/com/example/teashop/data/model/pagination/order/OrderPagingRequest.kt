package com.example.teashop.data.model.pagination.order

import com.example.teashop.data.model.pagination.Pagination

data class OrderPagingRequest (
    val pagination: Pagination = Pagination(),
    val filter: OrderFilter,
    val sorter: OrderSorter
)