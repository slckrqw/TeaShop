package com.example.teashop.data.model.order

enum class OrderStatus(var value: String){
    NEW("Новый"),
    CONFIRMED("Подтверждён"),
    IN_THE_WAY("В пути"),
    COMPLETED("Доставлен"),
    CANCELLED("Отменён")
}