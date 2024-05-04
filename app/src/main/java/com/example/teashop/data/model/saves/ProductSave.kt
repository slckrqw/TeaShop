package com.example.teashop.data.model.saves

data class ProductSave(
    val id: Long,
    val images: List<Long>,
    val packages: List<PackageSave>,
    val categoryId: Int,
    val article: String,
    val title: String,
    val description: String,
    val discount: Short,
    val active: Boolean
)
