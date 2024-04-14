package com.example.teashop.screen.screen.profile_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.teashop.reusable_interface.MakeTopCard
import androidx.navigation.NavController
import com.example.teashop.reusable_interface.MakeTopCard
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.data.Feedback
import com.example.teashop.data.Order
import com.example.teashop.data.OrderStatus
import com.example.teashop.data.Product
import com.example.teashop.navigation.Navigation
import com.example.teashop.navigation.Screen
import com.example.teashop.reusable_interface.MakeTopCard
import com.example.teashop.screen.screen.feedback_screen.MakeNewFeedbackScreen
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchOrderDescriptionScreen(navController: NavController, order: Order?){
    Navigation(navController = navController) {
        MakeOrderDescriptionScreen(navController = navController, order = order)
    }
}

@Composable
fun MakeOrderDescriptionScreen(navController: NavController, order: Order?){
    if(order != null) {
        LazyColumn {
            item {
                MakeTopCard(
                    drawableId = R.drawable.back_arrow,
                    textId = R.string.OrderDescription,
                    navController = navController
                )
            }
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Column(

                    ) {
                        Text(
                            text = "Заказ №${order.id}",
                            fontSize = 20.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W700,
                            color = Black10
                        )
                        OrderStatusText(order = order, fontSize = 15)
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
                ){
                    Column(

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
                Text(
                    text = "Товары",
                    fontSize = 15.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W700,
                    color = Black10
                )
                Column {
                    order.productList.forEach {
                        Row(

                        ){
                            Image(
                                painter = painterResource(it.imageResourceId),
                                contentDescription = null,
                                modifier = Modifier.size(80.dp)
                            )
                            Column(

                            ){
                                Text(
                                    text = stringResource(it.nameId),
                                    fontSize = 11.sp,
                                    fontFamily = montserratFamily,
                                    fontWeight = FontWeight.W600,
                                    color = Black10
                                )
                                Text(
                                    text = "${it.price}"
                                )
                            }
                        }
                    }
                }
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