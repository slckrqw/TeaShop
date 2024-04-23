package com.example.teashop.data.model.pagination.product

import com.example.teashop.data.model.variant.VariantType

data class ProductFilter(
    var onlyPopular: Boolean? = false,
    var onlyNew: Boolean? = false,
    var onlyFavorite: Boolean? = false,
    var categoryId: Int? = null,
    var searchString: String? = null,
    var inStock: Boolean? = false,
    var minPrice: Double? = 0.0,
    var maxPrice: Double? = 0.0,
    var variantTypes: MutableList<VariantType>? = mutableListOf()
)
