package com.example.teashop.data

data class Feedback(
    var userId: String = "",
    var rate: Int = 0,
    var content: String = "",
    var imageResourceId: Int = 0
)
