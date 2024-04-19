package com.example.teashop.data.enums

import android.annotation.SuppressLint

enum class UserRole {
    USER,
    ADMIN,
    MANAGER;

    private final var value: String = ""

    @SuppressLint("NotConstructor")
    fun UserRole(value: String){ //TODO don't know why it's here
        this.value = value
    }
}