package com.example.teashop.data.model.review

import com.example.teashop.data.model.image.Image
import java.time.ZonedDateTime

data class Review(
    var id: Long? = 1234,
    var createdDate: ZonedDateTime? = ZonedDateTime.now(),
    var userName: String? = "kirpich",
    var stars: Int? = 3,
    var reviewText: String? = "aboba",
    var images: List<Image>? = listOf(Image(),Image(),Image(),Image(),Image()),
    var productId: Long? = 1234,
    var productTitle: String? = "Зелёный чай",
    var productImage: Image? = Image()
)
