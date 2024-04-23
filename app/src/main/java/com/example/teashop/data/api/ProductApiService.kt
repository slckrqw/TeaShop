package com.example.teashop.data.api

import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.pagination.product.ProductResponse
import com.example.teashop.data.model.product.ProductFull
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @POST("/products/actions/search-by-filter")
    suspend fun getProductsByFilter(
        @Header("Authorization") token: String?,
        @Body request: ProductPagingRequest
    ): Response<ProductResponse>

    @PUT("/products/actions/favorite/{id}")
    suspend fun setFavorite(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<String>

    @GET("/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): Response<ProductFull>

    companion object {
        val productApiService: ProductApiService by lazy {
            retrofitBuilder.create(ProductApiService::class.java)
        }
    }
}