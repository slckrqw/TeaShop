package com.example.teashop.data.model.saves

data class ProductSave(
    var id: Long? = null,
    var images: List<Long?>? = null,
    var packages: List<PackageSave>? = null,
    var categoryId: Int? = null,
    var article: String? = null,
    var title: String? = null,
    var description: String? = null,
    var discount: Int? = null,
    var active: Boolean? = true
)
