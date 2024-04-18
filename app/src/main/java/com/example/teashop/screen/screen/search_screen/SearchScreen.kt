package com.example.teashop.screen.screen.search_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
import com.example.teashop.navigation.Navigation
import com.example.teashop.navigation.Screen
import com.example.teashop.reusable_interface.cards.MakeSearchCard

const val boxesHeight = 170
@Composable
fun LaunchSearchScreen(navController: NavController){
    Navigation(navController = navController){
        MakeSearchScreen(navController)
    }
}

@Composable
fun MakeSearchScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        MakeSearchCard()
        NewProductsBanner(onClick = {navController.navigate("catalog_screen/Новинки")})
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TeaProductsBanner(
                R.drawable.tea_production,
                "Чайная\nпродукция",
               onClick = {
                   navController.currentBackStackEntry?.savedStateHandle?.set("categoryList", DataSource().loadCategories())
                   navController.currentBackStackEntry?.savedStateHandle?.set("title", "Чайная продукция:")
                   navController.navigate(Screen.Category.route)
               }
            )
            TeaProductsBanner(
                R.drawable.tea_dishes,
                "Посуда\nдля чая",
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("categoryList", DataSource().loadCategories())
                    navController.currentBackStackEntry?.savedStateHandle?.set("title", "Посуда для чая:")
                    navController.navigate(Screen.Category.route)
                }
            )
        }
        PopularProductsBanner(onClick = {navController.navigate("catalog_screen/Популярное")})
    }
}
@Composable
fun NewProductsBanner(onClick: () -> Unit){
    Box(modifier = Modifier
        .padding(top = 20.dp, start = 10.dp, end = 10.dp)
        .fillMaxWidth()
        .height(boxesHeight.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Image(
            painterResource(id = R.drawable.new_products_search),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .clickable(onClick = { onClick() })
                .fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 15.dp, bottom = 30.dp)
        ) {
            Text(
                text = stringResource(R.string.newProductsCardSearchScreen1),
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W700,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = White10
            )
            Text(
                text = stringResource(R.string.newProductsCardSearchScreen2),
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                color = White10
            )
        }

    }
}

@Composable
fun RowScope.TeaProductsBanner(drawableId: Int, title: String, onClick: () -> Unit){
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .height(boxesHeight.dp)
            .width(150.dp)
            .weight(1f)
    ){
        Image(
            painterResource(drawableId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .clickable(onClick = {onClick() })
                .fillMaxSize()
        )
        Text(
            text = title,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W700,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            color = White10,
            modifier = Modifier.padding(top = 15.dp)
        )
    }
}

@Composable
fun PopularProductsBanner(onClick: () -> Unit){
    Box(modifier = Modifier
        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        .fillMaxWidth()
        .height(boxesHeight.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painterResource(id = R.drawable.popular_products_search),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable(onClick = { onClick() })
                .clip(RoundedCornerShape(15.dp))
                .fillMaxSize()
        )
        Text(
            text = stringResource(R.string.popularGoodsSearchScreen),
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W700,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            color = White10,
            modifier = Modifier.padding(start = 25.dp, bottom = 20.dp)
        )
    }
}