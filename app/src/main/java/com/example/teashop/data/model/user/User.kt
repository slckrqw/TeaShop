package com.example.teashop.data.model.user

import com.example.teashop.data.enums.UserRole
import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.bucket.Bucket
import java.time.ZonedDateTime

data class User(
    private val id: Long,
    private val role: UserRole,
    private val favorites: List<Long>,
    private val bucket: Bucket,
    private val addresses: List<Address>,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val teaBonuses: Int,
    private val ordersCnt: Int,
    private val createdTime: ZonedDateTime
)
