package com.example.teashop.data.model.pagination.product

import com.example.teashop.data.model.pagination.Pagination

data class ProductPagingRequest (
    val pagination: Pagination,
    val filter: ProductFilter,
    val sorter: ProductSorter
)