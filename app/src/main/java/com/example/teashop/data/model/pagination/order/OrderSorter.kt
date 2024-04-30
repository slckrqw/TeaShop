package com.example.teashop.data.model.pagination.order

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver

data class OrderSorter(
    var sortType: OrderSortType = OrderSortType.NEW
)

fun orderSorterSaver(): Saver<OrderSorter, Any> {
    return mapSaver(
        save = {sorter ->
            mapOf(
                "sortType" to sorter.sortType
            )
        },
        restore = { map ->
            OrderSorter(map["sortType"] as OrderSortType)
        }
    )
}