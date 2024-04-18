package com.example.teashop.screen.screen.profile_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.teashop.reusable_interface.cards.MakeTopCard
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.data.model.DataSource
import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.order.OrderStatus
import com.example.teashop.navigation.Navigation
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily


@Composable
fun LaunchOrdersScreen(navController: NavController){
    Navigation(navController = navController) {
        MakeOrdersScreen(navController = navController)
    }
}
@Composable
fun MakeOrdersScreen(navController: NavController){
    val ordersList = DataSource().loadOrders()
    LazyColumn {
        item {
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = "Мои заказы",
                navController = navController
            )
        }
        items(ordersList.size){order ->
            MakeOrderCard(order = ordersList[order], navController)
        }
    }
}

@Composable
fun MakeOrderCard(order: Order?, navController: NavController){
    if(order != null) {
        Card(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .height(170.dp)
                .clickable(onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("order", order)
                    navController.navigate("description_screen/$order")
                    }
                ),
            colors = CardDefaults.cardColors(containerColor = White10),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "№${order.id}",
                    fontSize = 15.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W700,
                    color = Black10
                )
                Text(
                    text = "Цена: ${order.totalCost} рублей",
                    fontSize = 10.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W400,
                    color = Black10
                )
                Text(
                    text = "Трек-номер: ${order.trackNumber}",
                    fontSize = 10.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W400,
                    color = Black10
                )
                OrderStatusText(order = order, 10)
                Row(

                ) {
                    order.productList.forEach {
                        Image(
                            painter = painterResource(it.imageResourceId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderStatusText(order: Order?, fontSize: Int){

    val fontStyle = TextStyle(
        fontSize = fontSize.sp,
        fontFamily = montserratFamily,
        fontWeight = FontWeight.W400
    )

    if(order != null) {
        Row(

        ) {
            Text(
                text = "Статус: ",
                style = fontStyle,
                color = Black10
            )
            when (order.status) {

                OrderStatus.NEW -> Text(
                    text = "Новый",
                    style = fontStyle,
                    color = Black10
                )

                OrderStatus.CONFIRMED -> Text(
                    text = "Подтверждён",
                    style = fontStyle,
                    color = Black10
                )

                OrderStatus.ON_THE_WAY -> Text(
                    text = "В пути",
                    style = fontStyle,
                    color = Black10
                )

                OrderStatus.DELIVERED -> Text(
                    text = "Доставлен",
                    style = fontStyle,
                    color = Green10
                )

                OrderStatus.CANCELLED -> Text(
                    text = "Отменён",
                    style = fontStyle,
                    color = Red10
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewOrders() {
    TeaShopTheme {
    }
}