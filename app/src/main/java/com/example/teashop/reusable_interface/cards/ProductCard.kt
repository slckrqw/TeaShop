package com.example.teashop.reusable_interface.cards

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.product.ProductShort
import com.example.teashop.data.model.saves.ProductToBucket
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.data.utils.reviewDeclension
import com.example.teashop.navigation.common.Screen
import com.example.teashop.screen.screen.product_screen.ProductViewModel
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily
import java.math.BigDecimal
import java.math.RoundingMode

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
fun RowOfCards(
    navController: NavController,
    product1: ProductShort?,
    product2: ProductShort?
){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        MakeProductCard(navController = navController, product = product1)
        if (product2 != null) {
            MakeProductCard(
                navController = navController,
                product = product2,
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RowScope.MakeProductCard(navController: NavController, product: ProductShort?) {
    var productWeight by remember { mutableStateOf(if ((product?.packages?.get(0)?.variantType
            ?: VariantType.FIFTY_GRAMS) == VariantType.PACK
    ) {
        VariantType.PACK
    } else {
        VariantType.FIFTY_GRAMS
    })}
    var expanded by remember{ mutableStateOf(false) }
    var heartIconId by remember {
        mutableIntStateOf(R.drawable.heart_icon_disabled)
    }
    var favorite by remember {
        mutableStateOf(product!!.favorite)
    }
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }
    val productViewModel: ProductViewModel = viewModel()

    if(product != null) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .weight(1f)
                .widthIn(0.dp, 300.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "productId", product.id
                        )
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "isFavorite", product.favorite
                        )
                        navController.navigate(
                            Screen.Product.route
                        )
                    }
                )
                .shadow(2.dp, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.TopEnd
        ) {
            Column(
                modifier = Modifier
                    .background(White10)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    Image(
                        rememberAsyncImagePainter(model = product.images[0].imageUrl),
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                }
                Text(
                    text = "Артикул: ${product.article}",
                    fontFamily = montserratFamily,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.W200,
                    color = Black10,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = product.title,
                    fontFamily = montserratFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W500,
                    color = Black10,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .wrapContentHeight(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.star),
                        tint = Yellow10,
                        modifier = Modifier
                            .size(10.dp),
                        contentDescription = null
                    )
                    Text(
                        text = product.averageRating.toString(),
                        fontFamily = montserratFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W500
                    )
                    Text(
                        text = "${product.countOfReviews}" +
                                reviewDeclension(product.countOfReviews),
                        fontFamily = montserratFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W300,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${
                            BigDecimal(
                                product.packages
                                    .first { it.variantType == productWeight || 
                                            it.variantType == VariantType.PACK }
                                    .price*(1-product.discount.toDouble()/100)
                            ).setScale(2, RoundingMode.HALF_UP)
                        } ₽",
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Text(
                        text = "${product
                            .packages
                            .first { it.variantType == productWeight ||
                                    it.variantType == VariantType.PACK}
                            .price} ₽",
                        fontFamily = montserratFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W300,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        modifier = Modifier.padding(top = 5.dp, start = 10.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp, top = 5.dp, end = 10.dp)
                            .weight(4f)
                            .height(30.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = productWeight.value,
                                fontFamily = montserratFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W200,
                                color = Black10,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Grey20)
                            ) {
                                product.packages.forEach { it ->
                                    DropdownItem(
                                        teaWeight = it.variantType,
                                        expandedChange = { expanded = it },
                                        weightChange = { productWeight = it }
                                    )
                                }
                            }
                        }
                    }
                    IconButton(
                        onClick = {
                            val token = tokenStorage.getToken(context)
                            if (token == null) {
                                makeToast(
                                    context,
                                    "Для действия необходимо авторизироваться"
                                )
                                navController.navigate(Screen.Log.route)
                                return@IconButton
                            }

                            val productToBucket = ProductToBucket(
                                product.packages.first { it.variantType == productWeight }.id,
                                1
                            )

                            productViewModel.addProductToBucket(
                                token,
                                productToBucket,
                                onSuccess = {
                                    makeToast(context, "Товар добавлен в корзину!")
                                },
                                onError = {
                                    makeToast(context, "Упс, что-то пошло не так")
                                }
                            )

                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Green10),
                        modifier = Modifier
                            .padding(end = 5.dp, bottom = 5.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Green10)
                            .size(30.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.product_basket),
                            tint = White10,
                            modifier = Modifier.size(15.dp),
                            contentDescription = null
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .clickableWithoutRipple(
                        interactionSource = MutableInteractionSource(),
                        onClick = {
                            val token = tokenStorage.getToken(context)
                            if (token == null) {
                                makeToast(
                                    context,
                                    "Для действия необходимо авторизироваться"
                                )
                                navController.navigate(Screen.Log.route)
                                return@clickableWithoutRipple
                            }

                            productViewModel.setFavorite(
                                token,
                                product.id,
                                onSuccess = {
                                    makeToast(context, it)
                                    favorite = !favorite
                                    product.favorite = favorite
                                    heartIconId = if (!favorite) {
                                        R.drawable.heart_icon_disabled
                                    } else {
                                        R.drawable.hearticon
                                    }
                                },
                                onError = {
                                    makeToast(context, "Упс, что-то пошло не так")
                                }
                            )
                        }
                    )
                    .padding(5.dp)
            ) {
                heartIconId = when (favorite) {
                    false -> R.drawable.heart_icon_disabled
                    true -> R.drawable.hearticon
                }
                Icon(
                    painterResource(heartIconId),
                    tint = Red10,
                    modifier = Modifier
                        .padding(start = 15.dp, bottom = 15.dp)
                        .size(20.dp),
                    contentDescription = null
                )
            }
        }
    }
}

private fun makeToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MakeProductCard2(navController: NavController, product: ProductShort?) {
    var productWeight by remember { mutableStateOf(if ((product?.packages?.get(0)?.variantType
            ?: VariantType.FIFTY_GRAMS) == VariantType.PACK
    ) {
        VariantType.PACK
    } else {
        VariantType.FIFTY_GRAMS
    })}
    var expanded by remember{ mutableStateOf(false) }
    var heartIconId by remember {
        mutableIntStateOf(R.drawable.heart_icon_disabled)
    }
    var favorite by remember {
        mutableStateOf(product!!.favorite)
    }
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }
    val productViewModel: ProductViewModel = viewModel()
    val dropMenuWidth = 120

    if(product != null) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .fillMaxWidth()
                .background(White10)
                .clickable(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "productId", product.id
                        )
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "isFavorite", product.favorite
                        )
                        navController.navigate(
                            Screen.Product.route
                        )
                    }
                )
                .shadow(2.dp, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.TopEnd
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(White10)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(200.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(product.images[0].imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .height(200.dp)
                        .padding(end = 10.dp)
                        .background(White10)
                        .weight(1f)
                ) {
                    Text(
                        text = product.title,
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Артикул: ${product.article}",
                        fontFamily = montserratFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.W200,
                        color = Black10
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painterResource(id = R.drawable.star),
                            tint = Yellow10,
                            modifier = Modifier
                                .size(10.dp),
                            contentDescription = null
                        )
                        Text(
                            text = product.averageRating.toString(),
                            fontFamily = montserratFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W500
                        )
                        Text(
                            text = "${product.countOfReviews}" +
                                    reviewDeclension(product.countOfReviews),
                            fontFamily = montserratFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W300,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "${BigDecimal(
                                product.packages
                                    .first { it.variantType == productWeight ||
                                            it.variantType == VariantType.PACK }
                                    .price*(1-product.discount.toDouble()/100)
                            ).setScale(2, RoundingMode.HALF_UP)} ₽",
                            fontFamily = montserratFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Text(
                            text = "${product
                                .packages
                                .first { it.variantType == productWeight ||
                                        it.variantType == VariantType.PACK}
                                .price} ₽",
                            fontFamily = montserratFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W300,
                            style = TextStyle(textDecoration = TextDecoration.LineThrough),
                            modifier = Modifier.padding(top = 5.dp, start = 10.dp)
                        )
                    }
                    Button(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                        modifier = Modifier
                            .padding(bottom = 10.dp, top = 10.dp)
                            .width(dropMenuWidth.dp)
                            .height(25.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = productWeight.value,
                                fontFamily = montserratFamily,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W200,
                                color = Black10,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Grey20)
                            ) {
                                product.packages.forEach { it ->
                                    DropdownItem(
                                        teaWeight = it.variantType,
                                        expandedChange = { expanded = it },
                                        weightChange = { productWeight = it }
                                    )
                                }
                            }
                        }
                    }
                    IconButton(
                        onClick = {
                            val token = tokenStorage.getToken(context)
                            if (token == null) {
                                makeToast(
                                    context,
                                    "Для действия необходимо авторизироваться"
                                )
                                navController.navigate(Screen.Log.route)
                                return@IconButton
                            }

                            val productToBucket = ProductToBucket(
                                product.packages.first { it.variantType == productWeight }.id,
                                1
                            )

                            productViewModel.addProductToBucket(
                                token,
                                productToBucket,
                                onSuccess = {
                                    makeToast(context, "Товар добавлен в корзину!")
                                },
                                onError = {
                                    makeToast(context, "Упс, что-то пошло не так")
                                }
                            )
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Green10),
                        modifier = Modifier
                            .padding(end = 5.dp, bottom = 5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Green10)
                            .width(dropMenuWidth.dp)
                            .height(25.dp)
                    ) {
                        Text(
                            text = "В корзину",
                            fontFamily = montserratFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W500,
                            color = White10
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .clickableWithoutRipple(
                        interactionSource = MutableInteractionSource(),
                        onClick = {
                            val token = tokenStorage.getToken(context)
                            if (token == null) {
                                makeToast(
                                    context,
                                    "Для действия необходимо авторизироваться"
                                )
                                navController.navigate(Screen.Log.route)
                                return@clickableWithoutRipple
                            }

                            productViewModel.setFavorite(
                                token,
                                product.id,
                                onSuccess = {
                                    makeToast(context, it)
                                    favorite = !favorite
                                    product.favorite = favorite
                                    heartIconId = if (!favorite) {
                                        R.drawable.heart_icon_disabled
                                    } else {
                                        R.drawable.hearticon
                                    }
                                },
                                onError = {
                                    makeToast(context, "Упс, что-то пошло не так")
                                }
                            )
                        }
                    )
                    .padding(5.dp)
            ) {
                heartIconId = when (favorite) {
                    false -> R.drawable.heart_icon_disabled
                    true -> R.drawable.hearticon
                }
                Icon(
                    painterResource(heartIconId),
                    tint = Red10,
                    modifier = Modifier
                        .padding(start = 15.dp, bottom = 15.dp)
                        .size(20.dp),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun DropdownItem(
    teaWeight: VariantType,
    dropMenuWidth: Int = 100,
    expandedChange: (Boolean) -> Unit,
    weightChange: (VariantType) -> Unit
){
    DropdownMenuItem(
        text = {
            Text(
                text = teaWeight.value,
                fontFamily = montserratFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.W200,
                color = Black10
            )
        },
        onClick = {
            expandedChange(false)
            weightChange(teaWeight)
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


