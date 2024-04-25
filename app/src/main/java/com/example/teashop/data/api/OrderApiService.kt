package com.example.teashop.data.api

import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.pagination.order.OrderPagingRequest
import com.example.teashop.data.model.pagination.order.OrderResponse
import com.example.teashop.data.model.saves.ClientOrderSave

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApiService {
    @GET("/orders/{id}")
    suspend fun getFullOrderInfo(
        @Header("Authorization") token: String?,
        @Path("id") id: Long
    ): Response<Order>

    @POST("/orders/actions/search-by-filter")
    suspend fun getOrdersByFilter(
        @Header("Authorization") token: String?,
        @Body request: OrderPagingRequest
    ): Response<OrderResponse>

    @POST("/orders")
    suspend fun saveOrder(
        @Header("Authorization") token: String?,
        @Body request: ClientOrderSave
    ): Response<Order>

    companion object {
        val orderApiService: OrderApiService by lazy {
            retrofitBuilder.create(OrderApiService::class.java)
        }
    }
}