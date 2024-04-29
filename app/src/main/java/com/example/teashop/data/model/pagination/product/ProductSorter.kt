package com.example.teashop.data.model.pagination.product

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver

data class ProductSorter(
    var sortType: ProductSortType = ProductSortType.POPULAR
)

fun productSorterSaver(): Saver<ProductSorter, Any> {
    return mapSaver(
        save = {sorter ->
           mapOf(
               "sortType" to sorter.sortType
           )
        },
        restore = { map ->
            ProductSorter(map["sortType"] as ProductSortType)
        }
    )
}