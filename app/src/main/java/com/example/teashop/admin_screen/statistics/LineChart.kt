package com.example.teashop.admin_screen.statistics

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.teashop.data.model.statistics.OrdersStatistics
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.White10

@Composable
fun LineChartBase(
    orderStat: List<OrdersStatistics>,
    stepSizeX: Int = 100,
){
    val pointsList = loadPoints(orderStat.sortedBy {
        it.date
    })

    val xAxisData = AxisData.Builder()
        .axisStepSize(stepSizeX.dp)
        .backgroundColor(White10)
        .steps(pointsList.size - 1)
        .labelData { i -> orderStat[i].date.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(maxOf(pointsList) - 1)
        .backgroundColor(White10)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            (i + 1).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsList,
                    LineStyle(
                        color = Green10
                    ),
                    IntersectionPoint(radius = 4.dp),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(color = Green10),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )
    LineChart(
        modifier = Modifier
        .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}