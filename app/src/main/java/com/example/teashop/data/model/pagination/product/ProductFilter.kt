package com.example.teashop.data.model.pagination.product

import com.example.teashop.data.model.variant.VariantType

data class ProductFilter(
    var onlyPopular: Boolean? = false,
    var onlyNew: Boolean? = false,
    var onlyFavorite: Boolean? = false,
    val categoryId: Int? = null,
    val searchString: String? = null,
    var inStock: Boolean? = false,
    var minPrice: Double? = null,
    var maxPrice: Double? = null,
    val variantTypes: MutableList<VariantType>? = null
)
