package com.example.teashop.data.model.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Long = 1234,
    val imageUrl: String = "https://07194ef6-a6a6c823-4c2a-4281-a81b-28fd021faace.s3.timeweb.cloud/grean-tea.webp"
): Parcelable
