package com.example.teashop.data.model.saves

import com.example.teashop.data.model.packages.ShortPackage
import com.example.teashop.data.model.recipient.Recipient

data class ClientOrderSave(
    private val recipient: Recipient,
    private val addressId: Long,
    private val shortPackages: List<ShortPackage>,
    private val isPayWithBonuses: Boolean
)
