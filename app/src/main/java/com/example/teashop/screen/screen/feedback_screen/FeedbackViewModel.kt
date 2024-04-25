package com.example.teashop.screen.screen.feedback_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.review.Review
import com.example.teashop.data.model.saves.ReviewSave
import com.example.teashop.data.repository.AccountingRepository
import com.example.teashop.data.repository.ReviewRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class FeedbackViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _reviews = MutableLiveData<List<Review?>>()
    private val _images = MutableLiveData<List<Long?>>()
    val review: LiveData<List<Review?>>
        get() = _reviews

    val images: LiveData<List<Long?>>
        get() = _images

    fun getAllReviewByProduct(
        token: String?,
        reviewPagingRequest: ReviewPagingRequest,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val bToken = token?.let {
                "Bearer $token"
            }
            val response = ReviewRepository().getAllReviewByProduct(bToken, reviewPagingRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    _reviews.value = it.data
                }
            } else {
                onError()
            }
        }
    }

    fun saveReview(
        files: List<MultipartBody.Part>,
        token: String,
        reviewSave: ReviewSave,
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

                if (reviewSave.images.isNullOrEmpty()) {
                    reviewSave.images = _images.value
                }
            }

            val response = ReviewRepository().saveReview("Bearer $token", reviewSave)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess()
                }
            } else {
                onError()
            }
        }
    }

    fun existsReview(
        token: String,
        productId: Long,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = ReviewRepository().existsReview("Bearer $token", productId)
            response.body()?.id?.let {
                onSuccess()
            }
        }
    }
}