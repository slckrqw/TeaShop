package com.example.teashop.screen.screen.feedback_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.review.Review
import com.example.teashop.data.repository.ReviewRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class FeedbackViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _reviews = MutableLiveData<List<Review?>>()
    val review: LiveData<List<Review?>>
        get() = _reviews

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
}