package com.example.teashop.admin_screen.statistics

import co.yml.charts.common.model.Point
import java.lang.Math.random
import kotlin.random.Random

fun loadPoints(): List<Point>{
    val pointsList = mutableListOf<Point>()
    for(i in 1..31){
        pointsList
            .add(
                Point(
                    i.toFloat(),
                    Random.nextInt(from = 0, until = 30).toFloat()
                )
            )
    }
    return pointsList
}

fun maxOf(pointsList: List<Point>): Int{
    var max = 0
    pointsList.forEach {
        if(max<it.y){
            max = it.y.toInt()
        }
    }
    return max
}
fun minOf(pointsList: List<Point>): Int{
    var min = maxOf(pointsList)
    pointsList.forEach {
        if(min>it.y){
            min = it.y.toInt()
        }
    }
    return min
}