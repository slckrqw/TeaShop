package com.example.teashop.screen.screen.profile_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.teashop.reusable_interface.cards.MakeTopCard
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.order.Order
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.cards.MakeSummaryCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
import java.time.format.DateTimeFormatter

@Composable
fun LaunchOrderDescriptionScreen(navController: NavController, order: Order?){
    Navigation(navController = navController) {
        MakeOrderDescriptionScreen(navController = navController,order = order)
    }
}

@Composable
fun MakeOrderDescriptionScreen(navController: NavController, order: Order?){
    if(order != null) {
        LazyColumn {
            item {
                MakeTopCard(
                    drawableId = R.drawable.back_arrow,
                    text = "Заказ № ${order.id}",
                    navController = navController
                )
            }
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 15.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Дата заказа: "+order.createdDate.format(DateTimeFormatter.ISO_OFFSET_DATE),
                            fontSize = 14.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W600,
                            color = Black10
                        )
                        OrderStatusText(order = order, fontSize = 15)
                        Text(
                            text = "Трек-номер: ${order.trackNumber}",
                            fontSize = 14.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W400,
                            color = Black10
                        )
                    }
                }
            }
            item{
                Card(
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                        .height(125.dp)
                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                ){
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ){
                        Text(
                            text = "Получатель",
                            fontSize = 15.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W700,
                            color = Black10
                        )
                        Text(
                            text = "${order.recipient.surname} ${order.recipient.name}, ${order.recipient.phoneNumber}",
                            fontSize = 14.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W400,
                            color = Black10
                        )
                        Text(
                            text = "Адрес доставки",
                            fontSize = 15.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W700,
                            color = Black10
                        )
                        Text(
                            text = "${order.address.city}, " +
                                    "${order.address.intercomCode}, " +
                                    "${order.address.entrance}, " +
                                    "${order.address.floor}, " +
                                    "${order.address.flat}",
                            fontSize = 14.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W400,
                            color = Black10
                        )
                    }
                }
            }
            item{
                Card(
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Товары",
                        fontSize = 15.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W700,
                        color = Black10,
                        modifier = Modifier.padding(top = 5.dp, start = 10.dp, bottom = 5.dp)
                    )
                }
            }
            items(order.productList.size){product ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = order.productList[product].images[0]),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = order.productList[product].title,
                                fontSize = 11.sp,
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.W600,
                                color = Black10
                            )
                            Text(
                                text = " ₽",
                                fontSize = 13.sp,
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.W400,
                                color = Black10
                            )
                        }
                    }
                }
            }
            item{
                MakeSummaryCard(productCnt = order.productList.size-1, productList = order.productList)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreviewOrderDescription() {
    TeaShopTheme {
    }
}