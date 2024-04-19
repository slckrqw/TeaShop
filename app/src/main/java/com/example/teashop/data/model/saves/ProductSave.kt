package com.example.teashop.data.model.saves

data class ProductSave(
    private val id: Long,
    private val images: List<Long>,
    private val packages: List<PackageSave>,
    private val categoryId: Int,
    private val article: String,
    private val title: String,
    private val description: String,
    private val discount: Short
)
