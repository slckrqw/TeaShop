package com.example.teashop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Long = 123456,
    val city: String = "Свят град Воронеж",
    val flat: Short = 1,
    val floor: Short = 1,
    val entrance: Short = 1,
    val intercomCode: String = "не работает"
):Parcelable
