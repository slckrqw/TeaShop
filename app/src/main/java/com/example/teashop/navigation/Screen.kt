package com.example.teashop.navigation

open class Screen (val route: String){
    object Main: Screen("main_screen")

    object Search: Screen("search_screen")

    object Basket: Screen("basket_screen")

    object Profile: Screen("profile_screen")

    object Catalog: Screen("catalog_screen/{topName}")

    object Product: Screen("product_screen/{product}")

    object Category: Screen("category_screen/{nameId}")

    object Feedback: Screen("feedback_screen/{product}")

    object NewFeedback: Screen("newFeedback_screen/{product}")

    object Order: Screen("order_screen")

    object Reg: Screen("reg_screen")

    object Log: Screen("log_screen")

    object Orders: Screen ("orders_screen")

    object OrderDescription: Screen("description_screen/{order}")
}