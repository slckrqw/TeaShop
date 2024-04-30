package com.example.teashop.data.api

import com.example.teashop.data.model.stripe.CustomerModel
import com.example.teashop.data.model.stripe.PaymentIntent
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface StripeApiService {
    @Headers("Authorization: Bearer $SECRET_KEY")
    @POST("v1/customers")
    suspend fun getCustomer() : Response<CustomerModel>

    @Headers("Authorization: Bearer $SECRET_KEY",
        "Stripe-Version: 2024-04-10")
    @POST("v1/ephemeral_keys")
    suspend fun getEphemeralKey(
        @Query("customer") customer: String
    ) : Response<CustomerModel>

    @Headers("Authorization: Bearer $SECRET_KEY",
        "Stripe-Version: 2024-04-10")
    @POST("v1/payment_intents")
    suspend fun getPaymentIntent(
        @Query("customer") customer: String,
        @Query("amount") amount: Int,
        @Query("currency") currency: String="RUB",
    ) : Response<PaymentIntent>

    companion object {
        val stripeApiService: StripeApiService by lazy {
            retrofitStripe.create(StripeApiService::class.java)
        }
    }
}