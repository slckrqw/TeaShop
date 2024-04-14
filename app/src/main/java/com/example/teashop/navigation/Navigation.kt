package com.example.teashop.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teashop.R
import com.example.teashop.reusable_interface.MakeNaviBar
import com.example.teashop.reusable_interface.NavigationBarIcon
import com.example.teashop.ui.theme.White10

@Composable
fun Navigation(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier.height(45.dp),
        bottomBar = {
           MakeNaviBar(navController = navController)
        },
    ){paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            content(paddingValues)
        }
    }
}