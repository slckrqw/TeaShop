package com.example.teashop.data.model.review

import com.example.teashop.data.model.image.Image
import java.time.ZonedDateTime

data class Review(
    var id: Long? = 0,
    var createdDate: ZonedDateTime? = ZonedDateTime.now(),
    var userName: String? = "",
    var stars: Int? = 0,
    var reviewText: String? = "",
    var images: List<Image>? = listOf(),
    var productId: Long? = 0,
    var productTitle: String? = "",
    var productImage: Image? = Image()
)
