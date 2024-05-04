package com.example.teashop.screen.screen.profile_screen.order

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.teashop.reusable_interface.cards.MakeTopCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teashop.R
import com.example.teashop.data.model.order.OrderShort
import com.example.teashop.data.model.order.OrderStatus
import com.example.teashop.data.model.pagination.order.OrderFilter
import com.example.teashop.data.model.pagination.order.OrderPagingRequest
import com.example.teashop.data.model.pagination.order.OrderSorter
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.MakeEmptyListScreen
import com.example.teashop.reusable_interface.cards.MakeOrderCard
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchOrdersScreen(
    navController: NavController,
    viewModel: OrderViewModel = viewModel(),
    orderSorter: OrderSorter = OrderSorter(),
    orderFilter: OrderFilter = OrderFilter()
){
    val orderView by viewModel.orders.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        tokenStorage.getToken(context)?.let {
            viewModel.getAllOrders(
                it,
                OrderPagingRequest(
                    filter = orderFilter,
                    sorter = orderSorter
                ),
                onError = {
                    Toast.makeText(context, "Не удалось получить ваши заказы", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    orderView?.let { orders ->
        Navigation(navController = navController) {
            MakeOrdersScreen(
                navController,
                orders
            )
        }
    }
}
@Composable
fun MakeOrdersScreen(
    navController: NavController,
    orders: List<OrderShort?>,
){
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        MakeTopCard(
            drawableId = R.drawable.back_arrow,
            text = "Мои заказы",
            navController = navController
        )
        if(orders.isEmpty()){
            MakeEmptyListScreen(type = "Заказов")
        } else {
            LazyColumn {
                items(orders.size, { orders[it]?.id ?: it}) { order ->
                    orders[order]?.let {
                        MakeOrderCard(order = it, navController, Screen.OrderDescription.route)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderStatusText(orderStatus: OrderStatus, fontSize: Int){
    val fontStyle = TextStyle(
        fontSize = fontSize.sp,
        fontFamily = montserratFamily,
        fontWeight = FontWeight.W400
    )
    Row {
        Text(
            text = "Статус: ",
            style = fontStyle,
            color = Green10
        )
        when (orderStatus) {
            OrderStatus.NEW -> Text(
                text = "Новый",
                style = fontStyle,
                color = Green10
            )
            OrderStatus.CONFIRMED -> Text(
                text = "Подтверждён",
                style = fontStyle,
                color = Green10
            )
            OrderStatus.IN_THE_WAY -> Text(
                text = "В пути",
                style = fontStyle,
                color = Green10
            )
            OrderStatus.COMPLETED -> Text(
                text = "Доставлен",
                style = fontStyle,
                color = Green10
            )
            OrderStatus.CANCELED -> Text(
                text = "Отменён",
                style = fontStyle,
                color = Red10
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewOrders() {
    TeaShopTheme {
    }
}