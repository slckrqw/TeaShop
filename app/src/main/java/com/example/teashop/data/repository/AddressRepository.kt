package com.example.teashop.data.repository

import com.example.teashop.data.api.AddressApiService.Companion.addressApiService
import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.saves.AddressSave
import retrofit2.Response

class AddressRepository {
    suspend fun getUserAddresses(
        token: String,
    ): Response<MutableList<Address>> {
        return addressApiService.getUserAddresses(token)
    }

    suspend fun createNewAddress(
        token: String,
        addressSave: AddressSave
    ): Response<Address> {
        return addressApiService.createNewAddress(token, addressSave)
    }

    suspend fun deleteAddress(
        token: String,
        id: Long
    ): Response<String> {
        return addressApiService.deleteAddress(token, id)
    }
}