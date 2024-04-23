package com.example.teashop.screen.screen.catalog_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.product.ProductShort
import com.example.teashop.data.repository.ProductRepository
import kotlinx.coroutines.launch

class CatalogScreenViewModel: ViewModel() {
    private val _products = MutableLiveData<List<ProductShort?>>()
    val product: LiveData<List<ProductShort?>>
        get() = _products

    fun getAllProducts(
        token: String?,
        productPagingRequest: ProductPagingRequest,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            val bToken = token?.let {
                "Bearer $token"
            }
            if (productPagingRequest.filter.maxPrice == 0.0) {
                productPagingRequest.filter.maxPrice = 1000000.0
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