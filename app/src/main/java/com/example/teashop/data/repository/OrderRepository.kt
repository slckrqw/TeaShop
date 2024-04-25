package com.example.teashop.data.repository

import com.example.teashop.data.api.OrderApiService.Companion.orderApiService
import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.pagination.order.OrderPagingRequest
import com.example.teashop.data.model.pagination.order.OrderResponse
import com.example.teashop.data.model.saves.ClientOrderSave
import retrofit2.Response

class OrderRepository {
    suspend fun getFullOrderInfo(
        token: String?,
        id: Long
    ): Response<Order> {
        return orderApiService.getFullOrderInfo(token, id)
    }

    suspend fun getOrdersByFilter(
        token: String?,
        request: OrderPagingRequest
    ): Response<OrderResponse> {
        return orderApiService.getOrdersByFilter(token, request)
    }

    suspend fun saveOrder(
        token: String?,
        request: ClientOrderSave
    ): Response<Order> {
        return orderApiService.saveOrder(token, request)
    }
}