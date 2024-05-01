package com.example.teashop.screen.screen.profile_screen.order

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.reusable_interface.cards.MakeSummaryCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchOrderDescriptionScreen(
    navController: NavController,
    orderId: Long?,
    viewModel: OrderViewModel = viewModel()
){
    val orderView by viewModel.userOrder.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        if (orderId != null) {
            tokenStorage.getToken(context)?.let {
                viewModel.getFullOrder(
                    it,
                    orderId,
                    onError = {
                        Toast.makeText(context, "Не удалось получить данный заказ", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    orderView?.let { order ->
        Navigation(navController = navController) {
            MakeOrderDescriptionScreen(navController = navController,order = order)
        }
    }
}

@Composable
fun MakeOrderDescriptionScreen(navController: NavController, order: Order){
    val totalCount = remember {
        order.packageOrders.sumOf {
            it.quantity
        }
    }
    LazyColumn {
        item {
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = "Заказ №${order.id}",
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
                        text = "Дата заказа: " + order.createdDate.toLocalDate(),
                        fontSize = 16.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W600,
                        color = Black10
                    )
                    OrderStatusText(orderStatus = order.status, 15)
                    Text(
                        text = "Трек-номер: ${order.trackNumber ?: "Отсутствует"}",
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
                        text = "${order.address.address}, " +
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
        items(order.packageOrders.size, { order.packageOrders[it].productTitle }){product ->
            val currentProduct = order.packageOrders[product]
            val variantTitle = when(currentProduct.type) {
                VariantType.FIFTY_GRAMS -> "50 гр."
                VariantType.HUNDRED_GRAMS -> "100 гр."
                VariantType.TWO_HUNDRED_GRAMS -> "200 гр."
                VariantType.FIVE_HUNDRED_GRAMS -> "500 гр."
                VariantType.PACK -> "шт."
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = currentProduct.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = currentProduct.productTitle,
                            fontSize = 11.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W600,
                            color = Black10,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${currentProduct.price} ₽ x $variantTitle x ${currentProduct.quantity} шт.",
                            fontSize = 13.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W400,
                            color = Black10,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
        item{
            MakeSummaryCard(
                productCnt = totalCount,
                bonusesAccrued = order.bonusesAccrued,
                bonusesSpent = order.bonusesSpent,
                totalCost = order.totalCost
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreviewOrderDescription() {
    TeaShopTheme {
    }
}