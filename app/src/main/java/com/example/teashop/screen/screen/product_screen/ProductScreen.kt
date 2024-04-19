package com.example.teashop.screen.screen.product_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.navigation.Navigation
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

@SuppressLint("UnnecessaryComposedModifier")
private fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) = composed(
    factory = {
        this.then(
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)

@Composable
fun LaunchProductScreen(navController: NavController, product: ProductFull?){
    Navigation(navController = navController) {
        MakeProductScreen(product = product, navController)
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MakeProductScreen(product: ProductFull?, navController: NavController){
    val heartColor: Color
    val heartIcon: Int
    var heartTemp by remember{mutableIntStateOf(0)}
    var expanded by remember{mutableStateOf(false)}
    var productWeight by remember{ mutableIntStateOf(120) }

    when(heartTemp){
        0 -> {
            heartColor = Green10
            heartIcon = R.drawable.heart_icon_disabled
        }
        1 -> {
            heartColor = Red10
            heartIcon = R.drawable.hearticon
        }
        else -> {
            heartTemp = 0
            heartColor = Green10
            heartIcon = R.drawable.heart_icon_disabled
        }
    }

    if(product != null) {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            item {
                Card(
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(360.dp)
                        ) {
                            Image(
                                painter = painterResource(product.imageResourceId),
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back_arrow),
                                    tint = Green10,
                                    modifier = Modifier
                                        .clickableWithoutRipple(
                                            interactionSource = MutableInteractionSource(),
                                            onClick = { navController.popBackStack() }
                                        )
                                        .padding(start = 10.dp, top = 10.dp)
                                        .size(25.dp),
                                    contentDescription = null
                                )
                                Box(
                                    modifier = Modifier
                                        .clickableWithoutRipple(
                                            interactionSource = MutableInteractionSource(),
                                            onClick = { heartTemp++ }
                                        )
                                        .padding(end = 10.dp, top = 10.dp)
                                        .size(30.dp),
                                    ) {
                                    Icon(
                                        painter = painterResource(heartIcon),
                                        tint = heartColor,
                                        modifier = Modifier.fillMaxSize(),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Артикул: ${product.id}",
                            fontFamily = montserratFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.W200,
                            color = Black10,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Text(
                            text = stringResource(product.nameId),
                            fontFamily = montserratFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = Black10,
                            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 5.dp, bottom = 10.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "${product.price} ₽",
                                    fontFamily = montserratFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W700
                                )
                                Text(
                                    text = "${product.previousPrice} ₽",
                                    fontFamily = montserratFamily,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W300,
                                    style = TextStyle(textDecoration = TextDecoration.LineThrough),
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(end = 10.dp)
                            ) {
                                Button(
                                    onClick = { expanded = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(30.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = RoundedCornerShape(
                                        topStart = 5.dp,
                                        bottomStart = 5.dp
                                    )
                                ) {
                                    Box(
                                        contentAlignment = Alignment.CenterStart,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = "$productWeight гр",
                                            fontFamily = montserratFamily,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.W200,
                                            color = Black10,
                                            modifier = Modifier.padding(start = 5.dp)
                                        )
                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            modifier = Modifier.background(Grey20),
                                        ) {
                                            DropdownItem(
                                                teaWeight = 120,
                                                expandedChange = { expanded = it },
                                                weightChange = { productWeight = it },
                                                dropMenuWidth = 100
                                            )
                                            DropdownItem(
                                                teaWeight = 240,
                                                expandedChange = { expanded = it },
                                                weightChange = { productWeight = it },
                                                dropMenuWidth = 100
                                            )
                                        }
                                    }
                                }
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(containerColor = Green10),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(100.dp),
                                    shape = RoundedCornerShape(
                                        topEnd = 5.dp,
                                        bottomEnd = 5.dp
                                    )
                                ) {
                                    Text(
                                        text = "В корзину",
                                        fontFamily = montserratFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = White10
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 5.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.bonus_collect),
                                modifier = Modifier.size(25.dp),
                                tint = Green10,
                                contentDescription = null
                            )
                            Text(
                                text = "+${product.bonusCnt} бонусных рублей",
                                fontFamily = montserratFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                        Icon(
                            painter = painterResource(R.drawable.info_icon),
                            tint = Green10,
                            modifier = Modifier
                                .padding(end = 15.dp)
                                .size(25.dp),
                            contentDescription = null
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.star),
                                tint = Yellow10,
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .size(25.dp),
                                contentDescription = null
                            )
                            Text(
                                text = "${product.rate}",
                                fontFamily = montserratFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                            Text(
                                text = "${product.rateCnt} отзывов",
                                fontFamily = montserratFamily,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.W300,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }

                        Button(
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
                                navController.navigate("feedback_screen/$product")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Green10),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .width(170.dp)
                                .height(25.dp)
                        ) {
                            Text(
                                text = "Перейти к отзывам",
                                fontFamily = montserratFamily,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.W500,
                                color = White10
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = White10)
                ) {
                    Text(
                        text = "Описание",
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                    )
                    Text(
                        text = product.description,
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(
                            start = 10.dp,
                            bottom = 5.dp,
                            end = 10.dp
                        ),
                        lineHeight = 13.sp
                    )
                }
            }
        }
    }
}
@Composable
fun DropdownItem(
    teaWeight: Int,
    expandedChange:(Boolean)->Unit,
    weightChange:(Int) -> Unit,
    dropMenuWidth: Int
){
    DropdownMenuItem(
        text = {
            Text(
                text = "$teaWeight гр",
                fontFamily = montserratFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.W200,
                color = Black10
            )
        },
        onClick = {
            weightChange(teaWeight)
            expandedChange(false)
        },
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .width(dropMenuWidth.dp)
            .height(30.dp)
            .background(Grey20)

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
    }
}