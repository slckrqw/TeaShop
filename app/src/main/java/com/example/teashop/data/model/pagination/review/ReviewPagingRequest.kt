package com.example.teashop.data.model.pagination.review

import com.example.teashop.data.model.pagination.Pagination

data class ReviewPagingRequest (
    val pagination: Pagination = Pagination(),
    val filter: ReviewFilter,
    val sorter: ReviewSorter
)