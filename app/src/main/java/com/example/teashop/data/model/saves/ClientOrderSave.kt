package com.example.teashop.data.model.saves

import com.example.teashop.data.model.packages.ShortOrderPackage
import com.example.teashop.data.model.recipient.Recipient

data class ClientOrderSave(
    private val recipientDto: Recipient,
    private val addressId: Long,
    private val shortOrderPackageDtos: List<ShortOrderPackage>,
    private val isPayWithBonuses: Boolean
)
