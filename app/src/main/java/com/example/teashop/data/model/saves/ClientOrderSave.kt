package com.example.teashop.data.model.saves

import com.example.teashop.data.model.packages.ShortOrderPackage
import com.example.teashop.data.model.recipient.Recipient

data class ClientOrderSave(
    val recipientDto: Recipient,
    val addressId: Long,
    val shortOrderPackageDtos: List<ShortOrderPackage>,
    val bonusesSpent: Int = 0
)
