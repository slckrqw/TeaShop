package com.example.teashop.data.model.recipient

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipient(
    var id: Long? = null,
    var name: String,
    var surname: String,
    var email: String,
    var phoneNumber: String? = null
):Parcelable
