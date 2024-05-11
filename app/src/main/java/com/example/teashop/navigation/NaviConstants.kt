package com.example.teashop.navigation

import com.example.teashop.R
import com.example.teashop.navigation.admin.AdminScreen
import com.example.teashop.navigation.common.Screen

object NaviConstants {
    val BottomNaviItems = listOf(
        NaviBarItem(
            icon = R.drawable.home_bottombar_icon_3,
            route = listOf(
                Screen.Main.route
            )
        ),
        NaviBarItem(
            icon = R.drawable.search_bottombar_icon,
            route = listOf(
                Screen.Search.route,
                Screen.Catalog.route,
                Screen.Product.route,
                Screen.Feedback.route,
                Screen.NewFeedback.route
            )
        ),
        NaviBarItem(
            icon = R.drawable.shop_bottombar_icon,
            route = listOf(
                Screen.Basket.route,
                Screen.Order.route,
                Screen.AddressChange.route
            )
        ),
        NaviBarItem(
            icon = R.drawable.profile_bottombar_icon,
            route = listOf(
                Screen.Profile.route,
                Screen.Reg.route,
                Screen.Log.route,
                Screen.Orders.route,
                Screen.OrderDescription.route,
                Screen.UserData.route,
                Screen.UserFeedback.route
            )
        )
    )
    val AdminNaviItems = listOf(
        NaviBarItem(
            icon = R.drawable.orders_admin_icon,
            route = listOf(
                AdminScreen.Orders.route,
                AdminScreen.Description.route
            )
        ),
        NaviBarItem(
            icon = R.drawable.orderdescription_admin_icon,
            route = listOf(
                AdminScreen.Products.route,
                AdminScreen.NewProduct.route
            )
        ),
        NaviBarItem(
            icon = R.drawable.products_admin_icon,
            route = listOf(
                AdminScreen.Statistics.route
            )
        ),
        NaviBarItem(
            icon = R.drawable.profile_bottombar_icon,
            route = listOf(
                AdminScreen.Profile.route,
                Screen.UserData.route
            )
        )
    )
}