package com.example.teashop.data.model.review

import android.os.Parcelable
import com.example.teashop.data.model.image.Image
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

data class Review(
    var id: Long = 1234,
    var createdTime: ZonedDateTime = ZonedDateTime.now(),
    var userName: String = "",
    var stars: Int = 0,
    var reviewText: String = "",
    var images: List<Image>? = listOf(Image()),
    var productId: Long = 1234,
    var productTitle: String = "Зелёный чай",
    var productImage: Image = Image()
)
