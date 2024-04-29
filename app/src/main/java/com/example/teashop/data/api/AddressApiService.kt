package com.example.teashop.data.api

import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.saves.AddressSave
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AddressApiService {
    @GET("/address")
    suspend fun getUserAddresses(
        @Header("Authorization") token: String,
    ): Response<MutableList<Address>>

    @POST("/address")
    suspend fun createNewAddress(
        @Header("Authorization") token: String,
        @Body addressSave: AddressSave
    ): Response<Address>

    @DELETE("/address/{id}")
    suspend fun deleteAddress(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<String>

    companion object {
        val addressApiService: AddressApiService by lazy {
            retrofitBuilder.create(AddressApiService::class.java)
        }
    }
}