package com.example.teashop.data.model.filters

import com.example.teashop.data.model.variant.VariantType

data class Filters(
    var priceMin: Double = 0.0,
    var priceMax: Double = 0.0,
    var packageType: VariantType = VariantType.FIFTY_GRAMS,
    var inStock: Boolean = false
)
