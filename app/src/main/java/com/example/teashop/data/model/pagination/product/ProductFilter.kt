package com.example.teashop.data.model.pagination.product

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
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

fun productFilterSaver(): Saver<ProductFilter, Any> {
    return mapSaver(
        save = { filter ->
            mapOf(
                "onlyPopular" to filter.onlyPopular,
                "onlyNew" to filter.onlyNew,
                "onlyFavorite" to filter.onlyFavorite,
                "categoryId" to filter.categoryId,
                "searchString" to filter.searchString,
                "inStock" to filter.inStock,
                "minPrice" to filter.minPrice,
                "maxPrice" to filter.maxPrice,
                "variantTypes" to filter.variantTypes
            )
        },
        restore = { map ->
            ProductFilter(
                onlyPopular = map["onlyPopular"] as Boolean,
                onlyNew = map["onlyNew"] as Boolean,
                onlyFavorite = map["onlyFavorite"] as Boolean,
                categoryId = map["categoryId"] as? Int?,
                searchString = map["searchString"] as? String?,
                inStock = map["inStock"] as Boolean,
                minPrice = map["minPrice"] as Double,
                maxPrice = map["maxPrice"] as Double,
                variantTypes = map["variantTypes"] as MutableList<VariantType>?
            )
        }
    )
}