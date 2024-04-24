package com.example.teashop.screen.screen.profile_screen.user_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.saves.UserSave
import com.example.teashop.data.model.user.User
import com.example.teashop.data.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.Response

class UserDataViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    fun saveUserInfo(token: String?, userSave: UserSave, onSuccess: (User) -> Unit, onError: () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            val response: Response<User>? = token?.let { UserRepository().saveUserInfo("Bearer $it", userSave) }
            if (response != null) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    }
                } else {
                    onError()
                }
            } else {
                onError()
            }
        }
    }
}