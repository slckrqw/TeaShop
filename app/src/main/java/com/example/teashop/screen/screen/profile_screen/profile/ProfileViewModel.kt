package com.example.teashop.screen.screen.profile_screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.user.User
import com.example.teashop.data.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

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