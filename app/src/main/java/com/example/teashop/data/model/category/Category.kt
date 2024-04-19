package com.example.teashop.data.model.category

import android.os.Parcelable
import com.example.teashop.data.model.image.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int = 1,
    val name: String = "chai",
    val image: Image = Image()
): Parcelable