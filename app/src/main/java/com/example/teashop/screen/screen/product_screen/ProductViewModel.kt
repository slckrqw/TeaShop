package com.example.teashop.screen.screen.product_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.saves.ProductToBucket
import com.example.teashop.data.repository.BucketRepository
import com.example.teashop.data.repository.ProductRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _product = MutableLiveData<ProductFull?>()
    val product: LiveData<ProductFull?>
        get() = _product

    fun setFavorite(
        token: String, id: Long,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response: Response<String> = ProductRepository().setFavorite("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(it)
                }
            } else {
                onError()
            }
        }
    }

    fun addProductToBucket(
        token: String,
        request: ProductToBucket,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = BucketRepository().addProductToBucket("Bearer $token", request)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess()
                }
            } else {
                onError()
            }
        }
    }

    fun getProductById(
        id: Long,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = ProductRepository().getProductById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _product.value = it
                }
            } else {
                onError()
            }
        }
    }
}