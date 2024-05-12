package com.example.teashop.admin_screen.orders

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.order.Order
import com.example.teashop.data.model.order.OrderStatus
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.admin.AdminNavigation
import com.example.teashop.reusable_interface.cards.MakeSummaryCard
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.reusable_interface.text_fields.MakeFullTextField
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchAdminDescription(
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
        AdminNavigation(navController = navController) {
            MakeOrderDescriptionScreen(
                navController = navController,
                order = order,
                orderViewModel = viewModel,
                context = context,
                tokenStorage = tokenStorage
            )
        }
    }
}

@Composable
fun MakeOrderDescriptionScreen(
    navController: NavController,
    order: Order,
    orderViewModel: OrderViewModel,
    context: Context,
    tokenStorage: TokenStorage
){
    val totalCount = remember {
        order.packageOrders.sumOf {
            it.quantity
        }
    }
    var expandedStatus by remember{
        mutableStateOf(false)
    }
    var statusText by remember{
        mutableStateOf(order.status.value)
    }
    LazyColumn {
        item {
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                MakeTopCard(
                    drawableId = R.drawable.back_arrow,
                    text = "Заказ №${order.id}",
                    navController = navController
                )
                Icon(
                    painter = painterResource(R.drawable.save),
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 15.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable(
                            onClick = {
                                tokenStorage.getToken(context)?.let {
                                    orderViewModel.updateStatusOrTrack(
                                        it,
                                        order.id,
                                        order.status.name,
                                        order.trackNumber?.trim(),
                                        onSuccess = {
                                            Toast.makeText(context, "Данные успешно сохранены!",
                                                Toast.LENGTH_SHORT).show()
                                            navController.popBackStack()
                                        },
                                        onError = {
                                            Toast.makeText(context, "Ошибка при сохранении данных",
                                                    Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            }
                        )
                        .size(20.dp),
                    tint = White10,
                    contentDescription = null
                )
            }
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
                        .padding(vertical = 10.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "Дата заказа: " + order.createdDate.toLocalDate(),
                        fontSize = 16.sp,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W600,
                        color = Black10,
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = "Cтатус",
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                        color = Black10,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Button(
                        onClick = {expandedStatus = true},
                        modifier = Modifier
                            .padding(bottom = 5.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                        border = BorderStroke(1.dp, Green10)
                    ) {
                        Text(
                            text = statusText,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W400,
                            fontSize = 15.sp,
                            color = Black10
                        )
                        DropdownMenu(
                            expanded = expandedStatus,
                            onDismissRequest = { expandedStatus = false },
                            modifier = Modifier
                                .background(Grey20)
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth()
                        ) {
                            OrderStatus.entries.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.value,
                                            fontFamily = montserratFamily,
                                            fontWeight = FontWeight.W400,
                                            fontSize = 15.sp,
                                            color = Black10,
                                        )
                                    },
                                    onClick = {
                                        expandedStatus = false
                                        statusText = it.value
                                        order.status = it
                                    },
                                    modifier = Modifier
                                        .background(Grey20)
                                        .padding(horizontal = 10.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                    MakeFullTextField(
                        header = "Трек номер",
                        inputValue = order.trackNumber,
                        onValueChange = {order.trackNumber = it},
                        bottomPadding = 0,
                        contextLength = 50,
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
                        text = if (order.address.flat == null) {
                            order.address.address
                        } else {
                            "${order.address.address}, ${order.address.flat}"
                        },
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
        items(order.packageOrders.size, { it }){product ->
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
                totalCost = order.totalCost - order.bonusesSpent
            )
        }
    }
}