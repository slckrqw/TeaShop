package com.example.teashop.data.model.order

enum class OrderStatus(val value: String){
    NEW("Новый"),
    CONFIRMED("Подтверждён"),
    ON_THE_WAY("В пути"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменён")
}