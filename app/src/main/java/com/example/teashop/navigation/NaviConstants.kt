package com.example.teashop.navigation

import com.example.teashop.R

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
}