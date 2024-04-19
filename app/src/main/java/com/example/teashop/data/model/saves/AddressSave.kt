package com.example.teashop.data.model.saves

data class AddressSave(
    val id: Long ,
    val city: String,
    val address: String,
    val flat: Short,
    val floor: Short,
    val entrance: Short,
    val intercomCode: String
)
