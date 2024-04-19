package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.enums.NaviBarIcons
import com.example.teashop.ui.theme.White10

var currentIcon = NaviBarIcons.MAIN

@Composable
fun MakeNaviBar(navController: NavController){

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
        BottomAppBar(
            actions = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NavigationBarIcon(R.drawable.home_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = { navController.navigate("main_screen") },
                        colorChange = {currentIcon = it},
                        screen = NaviBarIcons.MAIN,
                        iconColorList[0]
                    )
                    NavigationBarIcon(R.drawable.search_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = { navController.navigate("search_screen") },
                        colorChange = {currentIcon = it},
                        screen = NaviBarIcons.SEARCH,
                        iconColorList[1]
                    )
                    NavigationBarIcon(R.drawable.shop_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = { navController.navigate("basket_screen") },
                        colorChange = {currentIcon = it},
                        screen = NaviBarIcons.BASKET,
                        iconColorList[2]
                    )
                    NavigationBarIcon(R.drawable.profile_bottombar_icon).MakeNavigationBarIcon(
                        mutableActionChange = { navController.navigate("profile_screen") },
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
        )
    }
}