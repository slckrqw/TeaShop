package com.example.teashop.admin_screen.statistics

import co.yml.charts.common.model.Point
import com.example.teashop.data.model.statistics.OrdersStatistics

fun loadPoints(orderStat: List<OrdersStatistics>): List<Point>{
    val pointsList = mutableListOf<Point>()
    for(i in orderStat.indices){
        pointsList
            .add(
                Point(
                    i.toFloat(),
                    orderStat[i].countOfOrders.toFloat()
                )
            )
    }
    return pointsList
}

fun maxOf(pointsList: List<Point>): Int{
    return pointsList.maxOf { it.y }.toInt()
}