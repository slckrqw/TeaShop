package com.example.teashop.data.model.statistics

data class Statistics(
    val categoryStatistics: List<CategoryStatistics>,
    val ordersStatistics: List<OrdersStatistics>,
    val countOfOrders: Int,
    val countOfSales: Int,
    val totalPrice: Double
)