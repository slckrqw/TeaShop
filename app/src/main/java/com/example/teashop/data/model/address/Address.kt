package com.example.teashop.data.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Long = 123456,
    val city: String = "г. Воронеж",
    val address: String = "ул. Ашанская, д.24",
    val flat: Short? = 5,
    val floor: Short = 150,
    val entrance: Short? = 2,
    val intercomCode: String? = "не работает"
):Parcelable
