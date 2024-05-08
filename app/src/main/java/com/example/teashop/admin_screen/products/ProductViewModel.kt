package com.example.teashop.admin_screen.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.category.ParentCategory
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.product.ProductAccounting
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.saves.ProductSave
import com.example.teashop.data.repository.AccountingRepository
import com.example.teashop.data.repository.CategoryRepository
import com.example.teashop.data.repository.ProductRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ProductViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _products = MutableLiveData<List<ProductAccounting?>>()
    private val _product = MutableLiveData<ProductFull?>()
    private val _categoryList = MutableLiveData<List<Category>>()
    private val _images = MutableLiveData<List<Long?>>()

    val images: LiveData<List<Long?>>
        get() = _images
    val categoryList: LiveData<List<Category>>
        get() = _categoryList
    val products: LiveData<List<ProductAccounting?>>
        get() = _products
    val product: LiveData<ProductFull?>
        get() = _product

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

    fun loadCategories(parentCategory: ParentCategory) {
        viewModelScope.launch(exceptionHandler) {
            val categories = CategoryRepository().getCategories(parentCategory)
            _categoryList.value = categories
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

    fun createOrUpdateProduct(
        files: List<MultipartBody.Part>,
        token: String,
        productSave: ProductSave,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (files.isNotEmpty()) {
                val imageResponse = AccountingRepository().uploadImages(files)
                if (imageResponse.isSuccessful) {
                    imageResponse.body()?.let {
                        _images.value = it
                    }
                } else {
                    onError()
                }

                if (productSave.images.isNullOrEmpty()) {
                    productSave.images = _images.value
                }
            }

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