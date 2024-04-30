package com.example.teashop.data.model.stripe

data class PaymentIntent(
    val id: String,
    val client_secret: String
)