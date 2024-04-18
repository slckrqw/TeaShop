package com.example.teashop.data.model.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    var userId: String = "",
    var rate: Int = 0,
    var content: String = "",
    var imageResourceId: Int = 0
):Parcelable
