package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.NaviBarItem
import com.example.teashop.navigation.common.Screen
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.White10

@Composable
fun MakeNaviBar(navController: NavController, barIconList: List<NaviBarItem>){
    val tokenStorage = TokenStorage()
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth()) {
        NavigationBar(
            containerColor = White10,
            modifier = Modifier.height(45.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
           barIconList.forEach { naviItem ->
                NavigationBarItem(
                    selected = naviItem.route.contains(currentRoute),
                    onClick = {
                        if((naviItem.route[0] == Screen.Basket.route ||
                            naviItem.route[0] == Screen.Profile.route)&&
                            tokenStorage.getToken(context).isNullOrEmpty())
                        {
                            navController.navigate(Screen.Log.route)
                        }else {
                            navController.navigate(naviItem.route[0])
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(naviItem.icon),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
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
    }
}