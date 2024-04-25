package com.example.teashop.screen.screen.basket_screen.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.bucket.Bucket
import com.example.teashop.data.model.saves.ProductToBucket
import com.example.teashop.data.repository.BucketRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class BasketViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _bucket = MutableLiveData<Bucket?>()
    val bucket: LiveData<Bucket?>
        get() = _bucket

    fun getBucket(
        token: String,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = BucketRepository().getBucket("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    _bucket.value = it
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
        onError: (String?) -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = BucketRepository().addProductToBucket("Bearer $token", request)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess()
                }
            } else {
                onError(response.errorBody()?.string())
            }
        }
    }

    fun deletePack(
        token: String,
        packId: Long,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = BucketRepository().deletePack("Bearer $token", packId)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess()
                }
            } else {
                onError()
            }
        }
    }

    fun clearBucket(
        token: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = BucketRepository().clearBucket("Bearer $token")
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