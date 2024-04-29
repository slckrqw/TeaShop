package com.example.teashop.data.model.pagination.review

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver

data class ReviewSorter (
    var sortType: ReviewSortType = ReviewSortType.NEW
)

fun reviewSorterSaver(): Saver<ReviewSorter, Any> {
    return mapSaver(
        save = {sorter ->
            mapOf(
                "sortType" to sorter.sortType
            )
        },
        restore = { map ->
            ReviewSorter(map["sortType"] as ReviewSortType)
        }
    )
}