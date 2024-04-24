package com.example.teashop.screen.screen.profile_screen.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.auth.RegistrationRequest
import com.example.teashop.data.repository.AuthRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Response

class RegistrationViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    fun registration(registrationRequest: RegistrationRequest, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            val response: Response<String> = AuthRepository().registration(registrationRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(it)
                }
            } else {
                onError(response.errorBody()?.string()!!)
            }
        }
    }
}