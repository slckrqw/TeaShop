package com.example.teashop.screen.screen.basket_screen

import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.data.Feedback
import com.example.teashop.data.Product
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.MakeTopCard
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
fun LaunchBasketScreen(navController: NavController){
    Navigation(navController = navController){
        MakeBasketScreen(navController = navController)
    }
}
@Composable
fun MakeBasketScreen(navController: NavController){

    val basketList = DataSource().loadProducts()
    val productCnt = 1

    Column{
       MakeTopCard(drawableId = R.drawable.back_arrow, textId = R.string.Basket, iconSwitch = false, navController = navController)
        if (basketList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Корзина пуста",
                            fontFamily = montserratFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            color = Black10
                        )
                        Text(
                            text = "Вы ещё не добавили товары в корзину",
                            fontFamily = montserratFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500,
                            color = Grey10
                        )
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Green10)
                        ) {
                            Text(
                                text = "Перейти в каталог",
                                fontFamily = montserratFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                color = White10
                            )
                        }
                    }
                }
            }
        } else {
            LazyColumn {
                items(basketList.size) { basketItem ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = White10),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .height(125.dp)
                            .fillMaxWidth(),
                    ) {
                        Row(

                        ) {
                            Image(
                                painter = painterResource(basketList[basketItem].imageResourceId),
                                modifier = Modifier
                                    .widthIn(0.dp, 125.dp),
                                contentScale = ContentScale.FillBounds,
                                contentDescription = null
                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = stringResource(basketList[basketItem].nameId),
                                    fontFamily = montserratFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500,
                                    color = Black10,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp)
                                ) {
                                    Text(
                                        text = "${basketList[basketItem].price} ₽ x 50гр",
                                        fontFamily = montserratFamily,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.W700,
                                        color = Black10
                                    )
                                    Text(
                                        text = "+ ${basketList[basketItem].bonusCnt} бонусов",
                                        fontFamily = montserratFamily,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.W400,
                                        color = Green10
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Grey20),
                                        modifier = Modifier
                                            .width(120.dp)

                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    start = 15.dp,
                                                    end = 15.dp,
                                                    top = 5.dp,
                                                    bottom = 5.dp
                                                )
                                        ) {
                                            BasketIcon(icon = R.drawable.minus_icon)
                                            Text(
                                                text = "$productCnt"
                                            )
                                            BasketIcon(icon = R.drawable.plus_icon)
                                        }
                                    }
                                    IconButton(
                                        onClick = { /*TODO*/ },
                                        colors = IconButtonDefaults.iconButtonColors(
                                            containerColor = Grey20
                                        ),
                                        modifier = Modifier.size(30.dp)
                                    ) {
                                        BasketIcon(icon = R.drawable.cross_icon)
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Button(
                        onClick = { navController.navigate("order_screen") },
                        colors = ButtonDefaults.buttonColors(containerColor = Green10),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "К оформлению",
                            fontFamily = montserratFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            color = White10
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun BasketIcon(icon: Int){
    Icon(
        painter = painterResource(icon),
        tint = Green10,
        modifier = Modifier.size(20.dp),
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {

    }
}