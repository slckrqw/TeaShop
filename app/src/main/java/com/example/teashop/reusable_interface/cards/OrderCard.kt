package com.example.teashop.reusable_interface.cards

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.data.model.order.OrderShort
import com.example.teashop.screen.screen.profile_screen.order.OrderStatusText
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.format.DateTimeFormatter

@Composable
fun MakeOrderCard(order: OrderShort, navController: NavController, route: String){
    Card(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(170.dp)
            .clickable(onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("orderId", order.id)
                navController.navigate(route)
            }),
        colors = CardDefaults.cardColors(containerColor = White10),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "№${order.id}",
                    fontSize = 15.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W700,
                    color = Black10
                )
                Text(
                    text = order.createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    fontFamily = montserratFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W400,
                    color = Grey10
                )
            }
            Text(
                text = "Цена: ${
                    BigDecimal(
                        order.totalCost
                    ).setScale(1, RoundingMode.HALF_UP)} рублей",
                fontSize = 10.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                color = Black10
            )
            Text(
                text = "Трек-номер: ${order.trackNumber ?: "Отсутствует"}",
                fontSize = 10.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                color = Black10
            )
            OrderStatusText(orderStatus = order.status, 10)
            LazyRow {
                items(order.packageOrders.size) { index->
                    Image(
                        painter = rememberAsyncImagePainter(model = order.packageOrders[index].imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}