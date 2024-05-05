package com.example.teashop.admin_screen.statistics

data class PieChartData(
    var browserName: String?,
    var value: Float?
)

val getPieChartData = listOf(
    PieChartData("Чёрный чай", 34.68F),
    PieChartData("Зелёный чай", 16.60F),
    PieChartData("Пиалы", 16.15F),
    PieChartData("Белый чай", 15.62F)
)