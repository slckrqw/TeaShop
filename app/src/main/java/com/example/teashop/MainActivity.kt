package com.example.teashop

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teashop.data.DataSource
import com.example.teashop.main_screen.MainScreen
import com.example.teashop.reusable_interface.NavigationBarIcon
import com.example.teashop.search_screen.SearchScreen
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10

const val DEFAULT_BALANCE = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = Color.parseColor("#1FAF54")
            window.navigationBarColor=getColor(R.color.white)
            TeaShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Grey20
                ) {
                    TeaShopApp()
                }
            }
        }
    }
}

@Composable
fun TeaShopApp(){
    var screen by remember { mutableIntStateOf(1) }
    val iconColorList = remember{ mutableListOf(false, false, false, false)}
    when(screen) {
        1 -> {
            iconColorList[0] = true
            iconColorList[1] = false
            iconColorList[2] = false
            iconColorList[3] = false//TODO refactor this shit
        }
        2 -> {
            iconColorList[0] = false
            iconColorList[1] = true
            iconColorList[2] = false
            iconColorList[3] = false
        }
    }
    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
        modifier = Modifier.height(45.dp),
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                BottomAppBar(
                    actions = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            NavigationBarIcon(R.drawable.home_bottombar_icon).MakeNavigationBarIcon(
                                mutableActionChange = { screen = it },
                                1,
                                iconColorList[0]
                            )
                            NavigationBarIcon(R.drawable.search_bottombar_icon).MakeNavigationBarIcon(
                                mutableActionChange = { screen = it },
                                2,
                                iconColorList[1]
                            )
                            NavigationBarIcon(R.drawable.shop_bottombar_icon).MakeNavigationBarIcon(
                                mutableActionChange = { screen = it },
                                3,
                                iconColorList[2]
                            )
                            NavigationBarIcon(R.drawable.profile_bottombar_icon).MakeNavigationBarIcon(
                                mutableActionChange = { screen = it },
                                4,
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
        },
        content = {
                padding->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
               when(screen) {
                   1 -> {
                       MainScreen().LazyColumnMainScreen(productsList = DataSource().loadProducts())
                   }
                   2 -> {
                       SearchScreen().ColumnSearchScreen()
                   }
               }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
       TeaShopApp()
    }
}