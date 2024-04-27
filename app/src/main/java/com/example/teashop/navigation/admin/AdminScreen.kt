package com.example.teashop.navigation.admin

open class AdminScreen(val route: String) {
    object Orders: AdminScreen("orders_admin")

    object  Products: AdminScreen("products_admin")

    object Statistics: AdminScreen("statistics_admin")

    object  Profile: AdminScreen("profile_admin")
}