package com.example.teashop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipient(
    var id: Long = 1,
    var name: String = "Керил",
    var surname: String = "Кирпичный",
    var email: String = "amogus@gmail.com",
    var phoneNumber: String = "+7 (800) 555-35-35"
):Parcelable
