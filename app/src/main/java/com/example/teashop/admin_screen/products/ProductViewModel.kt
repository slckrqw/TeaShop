package com.example.teashop.admin_screen.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.product.ProductAccounting
import com.example.teashop.data.model.saves.ProductSave
import com.example.teashop.data.repository.AccountingRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _products = MutableLiveData<List<ProductAccounting?>>()
    val product: LiveData<List<ProductAccounting?>>
        get() = _products

    fun getProductsByFilter(
        token: String,
        request: ProductPagingRequest,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            request.filter.minPrice = null
            val response = AccountingRepository().getProductsByFilter("Bearer $token", request)
            if (response.isSuccessful) {
                response.body()?.let {
                    _products.value = it.data
                }
            } else {
                onError()
            }
        }
    }

    fun createOrUpdateProduct(
        token: String,
        productSave: ProductSave,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch() {
            val response = AccountingRepository().createNewProduct(token, productSave)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess()
                }
            } else {
                onError()
            }
        }
    }
}