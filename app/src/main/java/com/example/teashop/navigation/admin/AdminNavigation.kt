package com.example.teashop.navigation.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.teashop.navigation.NaviConstants
import com.example.teashop.reusable_interface.MakeNaviBar

@Composable
fun AdminNavigation(navController: NavController, content: @Composable (PaddingValues) -> Unit){
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            MakeNaviBar(navController = navController, barIconList = NaviConstants.AdminNaviItems)
        },
    ){paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            content(paddingValues)
        }
    }
}