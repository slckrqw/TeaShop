package com.example.teashop.data.repository

import com.example.teashop.data.api.CategoryApiService.Companion.categoryApiService
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.category.ParentCategory

class CategoryRepository {
    suspend fun getCategories(parentCategory: ParentCategory): List<Category> {
        return categoryApiService.getCategoryByParent(parentCategory.name)
    }
}