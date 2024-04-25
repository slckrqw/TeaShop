package com.example.teashop.screen.screen.profile_screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.review.Review
import com.example.teashop.data.model.user.User
import com.example.teashop.data.repository.ReviewRepository
import com.example.teashop.data.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _user = MutableLiveData<User?>()
    private val _reviews = MutableLiveData<List<Review?>>()
    val user: LiveData<User?>
        get() = _user
    val review: LiveData<List<Review?>>
        get() = _reviews

    fun getAllReviewByCurrent(
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

    fun deleteReview(
        token: String,
        reviewId: Long,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = ReviewRepository().deleteReview("Bearer $token", reviewId)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess()
                }
            } else {
                onError()
            }
        }
    }

    fun getLoggedUserInfo(token: String, onError: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
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

    fun deleteAccount(token: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            val response = UserRepository().deleteAccount("Bearer $token")
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