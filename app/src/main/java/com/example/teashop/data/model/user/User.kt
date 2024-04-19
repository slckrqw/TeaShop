package com.example.teashop.data.model.user

import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.bucket.Bucket
import java.time.ZonedDateTime

data class User(
    val id: Long,
    val role: UserRole,
    val favorites: List<Long>,
    val bucket: Bucket,
    val addresses: List<Address>,
    val name: String,
    val surname: String,
    val email: String,
    val teaBonuses: Int,
    val ordersCount: Int,
    val createdDate: ZonedDateTime
)
