package com.example.teashop.data

import androidx.compose.runtime.remember

data class ProductFilter(
    var categoryId: Int = 0,
    var searchString: String = "",
    var inStock: Boolean = false,
    var minPrice: Double = 0.0,
    var maxPrice: Double = 0.0,
    var weightList: MutableList<Boolean> = mutableListOf(false, false, false, false)
)