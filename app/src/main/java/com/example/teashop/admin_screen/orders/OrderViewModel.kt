package com.example.teashop.admin_screen.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.order.OrderShort
import com.example.teashop.data.model.pagination.order.OrderPagingRequest
import com.example.teashop.data.repository.OrderRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _orders = MutableLiveData<List<OrderShort?>>()
    private val _userOrder = MutableLiveData<Order?>()
    val orders: LiveData<List<OrderShort?>>
        get() = _orders
    val userOrder: LiveData<Order?>
        get() = _userOrder

    fun getAllOrders(
        token: String,
        orderPagingRequest: OrderPagingRequest,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (orderPagingRequest.filter.maxPrice == 0.0) {
                orderPagingRequest.filter.maxPrice = 1000000.0
            }
            val response = OrderRepository().getOrdersByFilter("Bearer $token", orderPagingRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    _orders.value = it.data
                    if (orderPagingRequest.filter.maxPrice == 1000000.0) {
                        orderPagingRequest.filter.maxPrice = 0.0
                    }
                }
            } else {
                onError()
            }
        }
    }

    fun getFullOrder(
        token: String,
        id: Long,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = OrderRepository().getFullOrderInfo("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _userOrder.value = it
                }
            } else {
                onError()
            }
        }
    }
}