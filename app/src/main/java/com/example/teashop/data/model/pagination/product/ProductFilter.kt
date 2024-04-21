package com.example.teashop.data.model.pagination.product

import com.example.teashop.data.model.variant.VariantType

data class ProductFilter(
    val onlyPopular: Boolean,
    val onlyNew: Boolean,
    val onlyFavorite: Boolean,
    val categoryId: Int,
    val searchString: String?,
    val inStock: Boolean,
    val minPrice: Double,
    val maxPrice: Double,
    val variantTypes: List<VariantType>?
)
