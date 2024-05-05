package com.example.teashop.data.model.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Long = 0,
    val imageUrl: String = ""
): Parcelable
