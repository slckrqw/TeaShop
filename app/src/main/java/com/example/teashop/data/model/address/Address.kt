package com.example.teashop.data.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Long = 0,
    val city: String = "",
    val address: String = "",
    val flat: Short? = 0,
    val floor: Short = 0,
    val entrance: Short? = 0,
    val intercomCode: String? = ""
):Parcelable
