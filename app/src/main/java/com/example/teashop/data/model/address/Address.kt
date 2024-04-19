package com.example.teashop.data.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Long = 123456,
    val city: String = "Свят град Воронеж",
    val address: String = "Амогус",
    val flat: Short? = 1,
    val floor: Short = 1,
    val entrance: Short? = 1,
    val intercomCode: String = "не работает"
):Parcelable
