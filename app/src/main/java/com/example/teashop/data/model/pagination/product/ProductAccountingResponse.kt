package com.example.teashop.data.model.pagination.product

import com.example.teashop.data.model.pagination.PageResult
import com.example.teashop.data.model.product.ProductAccounting

data class ProductAccountingResponse(
    val data: List<ProductAccounting>,
    val pagingCommand: PageResult
)