package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.teashop.R
import com.example.teashop.data.enums.NaviBarIcons
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.NaviConstants
import com.example.teashop.navigation.Screen
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.White10

var currentIcon = NaviBarIcons.MAIN

@Composable
fun MakeNaviBar(navController: NavController){
    val tokenStorage = TokenStorage()
    val context = LocalContext.current
    val iconColorList = remember { mutableListOf(false, false, false, false) }
    var colorListCnt = 0
    when (currentIcon) {
        NaviBarIcons.MAIN -> {
            while(colorListCnt<iconColorList.size){
                iconColorList[colorListCnt] = false
                colorListCnt++
            }
            iconColorList[0] = true
        }

        NaviBarIcons.SEARCH -> {
            while(colorListCnt<iconColorList.size){
                iconColorList[colorListCnt] = false
                colorListCnt++
            }
            iconColorList[1] = true
        }

        NaviBarIcons.BASKET -> {
            while(colorListCnt<iconColorList.size){
                iconColorList[colorListCnt] = false
                colorListCnt++
            }
            iconColorList[2] = true
        }

        NaviBarIcons.PROFILE -> {
            while(colorListCnt<iconColorList.size){
                iconColorList[colorListCnt] = false
                colorListCnt++
            }
            iconColorList[3] = true
        }
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        NavigationBar(
            containerColor = White10,
            modifier = Modifier.height(45.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            NaviConstants.BottomNaviItems.forEach {naviItem ->
                NavigationBarItem(
                    selected = currentRoute == naviItem.route,
                    onClick = {
                        if((naviItem.route == Screen.Basket.route ||
                            naviItem.route == Screen.Profile.route)&&
                            tokenStorage.getToken(context).isNullOrEmpty())
                        {
                            navController.navigate(Screen.Log.route)
                        }else {
                            navController.navigate(naviItem.route)
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(naviItem.icon),
                            contentDescription = null
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Grey10,
                        selectedIconColor = Green10,
                        indicatorColor = White10
                    )
                )
            }
        }
        /*BottomAppBar(
            actions = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NavigationBarIcon(R.drawable.home_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = { navController.navigate(Screen.Main.route) },
                        colorChange = {currentIcon = it},
                        screen = NaviBarIcons.MAIN,
                        iconColorList[0]
                    )
                    NavigationBarIcon(R.drawable.search_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = { navController.navigate(Screen.Search.route) },
                        colorChange = {currentIcon = it},
                        screen = NaviBarIcons.SEARCH,
                        iconColorList[1]
                    )
                    NavigationBarIcon(R.drawable.shop_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = {
                            if (tokenStorage.getToken(context).isNullOrEmpty()) {
                                navController.navigate(Screen.Log.route)
                            } else {
                                navController.navigate(Screen.Basket.route)
                            }
                        },
                        colorChange = {currentIcon = it},
                        screen = NaviBarIcons.BASKET,
                        iconColorList[2]
                    )
                    NavigationBarIcon(R.drawable.profile_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = {
                            if (tokenStorage.getToken(context).isNullOrEmpty()) {
                                navController.navigate(Screen.Log.route)
                            } else {
                                navController.navigate(Screen.Profile.route)
                            }
                        },
                        colorChange = {currentIcon = it},
                        screen = NaviBarIcons.PROFILE,
                        iconColorList[3]
                    )
                }
            },
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth(),
            containerColor = White10,
            contentPadding = PaddingValues(0.dp)
        )*/
    }
}