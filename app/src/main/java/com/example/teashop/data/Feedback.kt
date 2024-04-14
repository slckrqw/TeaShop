package com.example.teashop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Feedback(
    var userId: String = "",
    var rate: Int = 0,
    var content: String = "",
    var imageResourceId: Int = 0
):Parcelable
