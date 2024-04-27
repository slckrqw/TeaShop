package com.example.teashop.navigation

import com.example.teashop.R
import com.example.teashop.navigation.admin.AdminScreen
import com.example.teashop.navigation.common.Screen

object NaviConstants {
    val BottomNaviItems = listOf(
        NaviBarItem(
            icon = R.drawable.home_bottombar_icon,
            route = Screen.Main.route
        ),
        NaviBarItem(
            icon = R.drawable.search_bottombar_icon,
            route = Screen.Search.route
        ),
        NaviBarItem(
            icon = R.drawable.shop_bottombar_icon,
            route = Screen.Basket.route
        ),
        NaviBarItem(
            icon = R.drawable.profile_bottombar_icon,
            route = Screen.Profile.route
        )
    )
    val AdminNaviItems = listOf(
        NaviBarItem(
            icon = R.drawable.orders_admin_icon,
            route = AdminScreen.Orders.route
        ),
        NaviBarItem(
            icon = R.drawable.orderdescription_admin_icon,
            route = AdminScreen.Products.route
        ),
        NaviBarItem(
            icon = R.drawable.products_admin_icon,
            route = AdminScreen.Statistics.route
        ),
        NaviBarItem(
            icon = R.drawable.profile_bottombar_icon,
            route = AdminScreen.Profile.route
        )
    )
}