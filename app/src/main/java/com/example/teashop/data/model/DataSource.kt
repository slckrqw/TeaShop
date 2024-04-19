package com.example.teashop.data.model

import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.order.OrderStatus
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.product.ProductShort
import com.example.teashop.data.model.review.Review

class DataSource {
    fun loadShortProducts():List<ProductShort?>{
        return listOf(
            ProductShort(),
            ProductShort(),
            ProductShort(),
            ProductShort(),
            ProductShort(),
            ProductShort(),
        )
    }

    fun loadFullProducts(): List<ProductFull?>{
        return listOf(
            ProductFull(),
            ProductFull(),
            ProductFull(),
            ProductFull(),
            ProductFull(),
            ProductFull(),
        )
    }

    fun loadFeedback(): List<Review>{
        return listOf(
            Review(),
            Review(),
            Review(),
            Review(),
            Review()
        )
    }

    fun loadOrders(): List<Order>{
        return listOf(
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
            Order(id = 1234567890, bonusesAccrued = 15, bonusesSpent = 5, status = OrderStatus.CONFIRMED, totalCost = 5000.0, trackNumber = "124151514124"),
        )
    }
}