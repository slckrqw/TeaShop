package com.example.teashop.navigation.admin

open class AdminScreen(val route: String) {
    object Orders: AdminScreen("orders_admin")

    object Description: AdminScreen("description_admin")

    object  Products: AdminScreen("products_admin")

    object NewProduct: AdminScreen("new_product_admin")

    object Statistics: AdminScreen("statistics_admin")

    object  Profile: AdminScreen("profile_admin")
}