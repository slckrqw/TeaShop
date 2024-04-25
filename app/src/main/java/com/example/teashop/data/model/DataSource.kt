package com.example.teashop.data.model

import com.example.teashop.data.model.product.ProductFull

class DataSource {
    fun loadFullProducts(): List<ProductFull?>{
        return listOf(
            ProductFull(),
            ProductFull(),
            ProductFull(),
            ProductFull(),
            ProductFull(),
            ProductFull(),
        )
    }
}