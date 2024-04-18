package com.example.teashop.data.model.product

data class ProductFilter(
    var categoryId: Int = 0,
    var searchString: String = "",
    var inStock: Boolean = false,
    var minPrice: Double = 0.0,
    var maxPrice: Double = 0.0,
    var weightList: MutableList<Boolean> = mutableListOf(false, false, false, false)
)