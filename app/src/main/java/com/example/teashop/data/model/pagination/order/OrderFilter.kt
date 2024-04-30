package com.example.teashop.data.model.pagination.order

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import com.example.teashop.data.model.order.OrderStatus
import java.time.ZonedDateTime

data class OrderFilter (
    var byCurrentUser: Boolean = true,
    var minPrice: Double? = 0.0,
    var maxPrice: Double? = null,
    var dateFrom: ZonedDateTime? = null,
    var dateTo: ZonedDateTime? = null,
    var status: OrderStatus? = null
)

fun orderFilterSaver(): Saver<OrderFilter, Any> {
    return mapSaver(
        save = { filter ->
            mapOf(
                "byCurrentUser" to filter.byCurrentUser,
                "minPrice" to filter.minPrice,
                "maxPrice" to filter.maxPrice,
                "dateFrom" to filter.dateFrom,
                "dateTo" to filter.dateTo,
                "status" to filter.status
            )
        },
        restore = { map ->
            OrderFilter(
                byCurrentUser = map["byCurrentUser"] as Boolean,
                minPrice = map["minPrice"] as Double,
                maxPrice = map["maxPrice"] as Double?,
                dateFrom = map["dateFrom"] as ZonedDateTime?,
                dateTo = map["dateTo"] as ZonedDateTime?,
                status = map["status"] as OrderStatus?
            )
        }
    )
}