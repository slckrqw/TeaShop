package com.example.teashop.screen.screen.basket_screen.basket

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.bucket.Bucket
import com.example.teashop.data.model.saves.ProductToBucket
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.data.utils.bonusDeclension
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.buttons.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchBasketScreen(
    navController: NavController,
    viewModel: BasketViewModel = viewModel()
){
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }
    val bucketView by viewModel.bucket.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getBucket(
            tokenStorage.getToken(context)!!,
            onError = {
                Toast.makeText(context, "Не удалось получить корзину", Toast.LENGTH_SHORT).show()
            }
        )
    }

    bucketView?.let { bucket ->
        Navigation(navController = navController){
            MakeBasketScreen(
                navController,
                bucket,
                viewModel,
                tokenStorage,
                context
            )
        }
    }
}

@Composable
fun MakeBasketScreen(
    navController: NavController,
    bucket: Bucket,
    viewModel: BasketViewModel,
    tokenStorage: TokenStorage,
    context: Context
){
    Column{
       MakeTopCard(
           drawableId = R.drawable.back_arrow,
           text = "Корзина",
           iconSwitch = false,
           navController = navController
       )
        if (bucket.products.isNullOrEmpty()) {
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
                            onClick = {
                                navController.navigate(Screen.Search.route)
                            },
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
                bucket.products.let { packages ->
                    items(packages.size, { packages[it].packId }) { basketItem ->
                        val currentItem = packages[basketItem]
                        val variantTitle = when(currentItem.variant.title) {
                            VariantType.FIFTY_GRAMS -> "50 гр."
                            VariantType.HUNDRED_GRAMS -> "100 гр."
                            VariantType.TWO_HUNDRED_GRAMS -> "200 гр."
                            VariantType.FIVE_HUNDRED_GRAMS -> "500 гр."
                            VariantType.PACK -> "шт."
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = White10),
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .height(125.dp)
                                .fillMaxWidth(),
                        ) {
                            Row{
                                Image(
                                    painter = rememberAsyncImagePainter(currentItem.product.images[0].imageUrl),
                                    modifier = Modifier
                                        .widthIn(125.dp, 125.dp),
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
                                        text = currentItem.product.title,
                                        fontFamily = montserratFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W500,
                                        color = Black10,
                                        modifier = Modifier.padding(bottom = 5.dp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 10.dp)
                                    ) {
                                        Text(
                                            text = "${currentItem.price} ₽ x $variantTitle",
                                            fontFamily = montserratFamily,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.W700,
                                            color = Black10,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "+ ${currentItem.plusTeaBonuses}" +
                                                    bonusDeclension(currentItem.plusTeaBonuses),
                                            fontFamily = montserratFamily,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.W400,
                                            modifier = Modifier
                                                .padding(start = 5.dp),
                                            color = Green10,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
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
                                                BasketIcon(
                                                    icon = R.drawable.minus_icon,
                                                    onClick = {
                                                        tokenStorage.getToken(context)?.let {
                                                            if ((currentItem.quantityInBucket - 1) != 0) {
                                                                viewModel.addProductToBucket(
                                                                    it,
                                                                    ProductToBucket(
                                                                        currentItem.packId,
                                                                        currentItem.quantityInBucket - 1
                                                                    ),
                                                                    onSuccess = {
                                                                        navController.navigate(
                                                                            Screen.Basket.route)
                                                                    },
                                                                    onError = {error ->
                                                                        error?.let {errorStr ->
                                                                            makeToast(
                                                                                context,
                                                                                errorStr
                                                                            )
                                                                        }
                                                                    }
                                                                )
                                                            } else {
                                                                viewModel.deletePack(
                                                                    it,
                                                                    currentItem.packId,
                                                                    onSuccess = {
                                                                        makeToast(
                                                                            context,
                                                                            "Продукт удален из корзины"
                                                                        )
                                                                        navController.navigate(
                                                                            Screen.Basket.route)
                                                                    },
                                                                    onError = {
                                                                        makeToast(
                                                                            context,
                                                                            "Не удалось удалить продукт"
                                                                        )
                                                                    }
                                                                )
                                                            }
                                                        }
                                                    }
                                                )
                                                Text(
                                                    text = currentItem.quantityInBucket.toString()
                                                )
                                                BasketIcon(
                                                    icon = R.drawable.plus_icon,
                                                    onClick = {
                                                        tokenStorage.getToken(context)?.let {
                                                            viewModel.addProductToBucket(
                                                                it,
                                                                ProductToBucket(
                                                                    currentItem.packId,
                                                                    currentItem.quantityInBucket + 1
                                                                ),
                                                                onSuccess = {
                                                                    navController.navigate(Screen.Basket.route)
                                                                },
                                                                onError = {error ->
                                                                    error?.let {errorStr ->
                                                                        makeToast(
                                                                            context,
                                                                            errorStr
                                                                        )
                                                                    }
                                                                }
                                                            )
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                        IconButton(
                                            onClick = {},
                                            colors = IconButtonDefaults.iconButtonColors(
                                                containerColor = Grey20
                                            ),
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            BasketIcon(
                                                icon = R.drawable.cross_icon,
                                                onClick = {
                                                    tokenStorage.getToken(context)?.let {
                                                        viewModel.deletePack(
                                                            it,
                                                            currentItem.packId,
                                                            onSuccess = {
                                                                makeToast(
                                                                    context,
                                                                    "Продукт удален из корзины"
                                                                )
                                                                navController.navigate(Screen.Basket.route)
                                                            },
                                                            onError = {
                                                                makeToast(
                                                                    context,
                                                                    "Не удалось удалить продукт"
                                                                )
                                                            }
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    MakeAgreeBottomButton(
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("bucket", bucket)
                            navController.navigate(Screen.Order.route)
                        },
                        text = "К оформлению"
                    )
                }
            }
        }
    }
}

private fun makeToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

@Composable
fun BasketIcon(icon: Int, onClick: () -> Unit){
    Icon(
        painter = painterResource(icon),
        tint = Green10,
        modifier = Modifier.size(20.dp)
            .clickable(
                onClick = {
                    onClick()
                }
            ),
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {

    }
}