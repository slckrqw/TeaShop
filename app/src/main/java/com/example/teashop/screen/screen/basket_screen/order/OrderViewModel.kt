package com.example.teashop.screen.screen.basket_screen.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.saves.AddressSave
import com.example.teashop.data.model.saves.ClientOrderSave
import com.example.teashop.data.model.user.User
import com.example.teashop.data.repository.AddressRepository
import com.example.teashop.data.repository.OrderRepository
import com.example.teashop.data.repository.UserRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _addresses = MutableLiveData<MutableList<Address>>()
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user
    val addresses: LiveData<MutableList<Address>>
        get() = _addresses

    fun getUserAddresses(
        token: String,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = AddressRepository().getUserAddresses("Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    _addresses.value = it
                }
            } else {
                onError()
            }
        }
    }

    fun createAddress(
        token: String,
        addressSave: AddressSave,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = AddressRepository().createNewAddress("Bearer $token", addressSave)
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess()
                }
            } else {
                onError()
            }
        }
    }

    fun deleteAddress(
        token: String,
        id: Long,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = AddressRepository().deleteAddress("Bearer $token", id)
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


    fun createOrder(
        token: String,
        request: ClientOrderSave,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            val response = OrderRepository().saveOrder("Bearer $token", request)
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