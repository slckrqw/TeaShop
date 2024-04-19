package com.example.teashop.data.model.bucket

import java.time.ZonedDateTime

data class Bucket(
    val id: Long,
    val userId: Long,
    val totalSumWithDiscount: Double,
    val totalDiscount: Double,
    val plusTeaBonuses: Int,
    val product: List<PackageBucket>?,
    val modifiedDate: ZonedDateTime
)
