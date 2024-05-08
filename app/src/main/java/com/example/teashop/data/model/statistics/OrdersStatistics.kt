package com.example.teashop.data.model.statistics

import java.time.LocalDate

data class OrdersStatistics(
    val date: LocalDate,
    val countOfOrders: Int
)