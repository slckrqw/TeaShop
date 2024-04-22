package com.example.teashop.data.repository

import com.example.teashop.data.api.ProductApiService.Companion.productApiService
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.pagination.product.ProductResponse
import retrofit2.Response

class ProductRepository {
    suspend fun getProductsByFilter(
        token: String?,
        request: ProductPagingRequest
    ): Response<ProductResponse> {
        return productApiService.getProductsByFilter(token, request)
    }

    suspend fun setFavorite(token: String, id: Long): Response<String> {
        return productApiService.setFavorite(token, id)
    }
}