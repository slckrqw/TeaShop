package com.example.teashop.data.model.pagination.product

import com.example.teashop.data.model.pagination.PageResult
import com.example.teashop.data.model.product.ProductShort

data class ProductResponse(
    val data: List<ProductShort>,
    val pagingCommand: PageResult
)