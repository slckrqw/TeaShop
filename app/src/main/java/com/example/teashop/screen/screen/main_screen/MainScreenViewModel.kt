package com.example.teashop.screen.screen.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.product.ProductShort
import com.example.teashop.data.model.user.User
import com.example.teashop.data.repository.ProductRepository
import com.example.teashop.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {
    private val _user = MutableLiveData<User?>()
    private val _products = MutableLiveData<List<ProductShort?>>()
    val user: LiveData<User?>
        get() = _user
    val product: LiveData<List<ProductShort?>>
        get() = _products

    fun getLoggedUserInfo(token: String, onError: () -> Unit) {
        viewModelScope.launch {
            val response = UserRepository().getLoggedUserInfo("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    _user.value = it
                }
            } else {
                onError()
            }
        }
    }

    fun getAllPopularProducts(
        token: String?,
        productPagingRequest: ProductPagingRequest,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            val bToken = token?.let {
                "Bearer $token"
            }
            val response = ProductRepository().getProductsByFilter(bToken, productPagingRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    _products.value = it.data
                }
            } else {
                onError()
            }
        }
    }
}