package com.example.teashop.data.api

import com.example.teashop.data.model.category.Category
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApiService {
    @GET("/category")
    suspend fun getCategoryByParent(@Query("parent") parent: String): List<Category>

    companion object {
        val categoryApiService: CategoryApiService by lazy {
            retrofitBuilder.create(CategoryApiService::class.java)
        }
    }
}
