package com.example.teashop.data.model.bucket

import java.time.ZonedDateTime

data class Bucket(
    private val id: Long,
    private val userId: Long,
    private val totalSumWithDiscount: Double,
    private val totalDiscount: Double,
    private val plusTeaBonuses: Int,
    private val product: List<PackageBucket>,
    private val modifiedDate: ZonedDateTime
)
