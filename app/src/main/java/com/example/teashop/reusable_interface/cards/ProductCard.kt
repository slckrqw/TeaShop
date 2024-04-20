package com.example.teashop.reusable_interface.cards

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
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.packages.PackageShort
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.product.ProductShort
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.navigation.Screen
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily
import javax.sql.DataSource

var productWeight = VariantType.FIVE_HUNDRED_GRAMS

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
        MakeProductCard(navController = navController,product = product2)
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun RowScope.MakeProductCard(navController: NavController, product: ProductShort?) {
    var productWeight by remember { mutableStateOf(VariantType.FIFTY_GRAMS) }
    var iconClicksCnt by remember {
        mutableIntStateOf(1)
    }
    var expanded by remember{ mutableStateOf(false) }
    var heartIconId: Int
    val dropMenuWidth = 100

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
                            "product", com.example.teashop.data.model.DataSource().loadFullProducts()[0]
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
                    modifier = Modifier.padding(start = 10.dp)
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
                        text = "${product.countOfReviews} отзывов",
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
                        text = "${product.packages[0].price*(1-(product.discount.toDouble()/100))} ₽",
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Text(
                        text = "${product.packages[0].price} ₽",
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
                            DropDownMenu(
                                dropMenuWidth = dropMenuWidth,
                                expanded = expanded,
                                expandedChange = {expanded = it}
                            )
                        }
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Green10),
                        modifier = Modifier
                            .padding(end = 5.dp, bottom = 5.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Green10)
                            .size(30.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.product_basket),//TODO fix size of basket icon
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
                        onClick = { iconClicksCnt++ }
                    )
                    .padding(5.dp)
            ) {
                when (iconClicksCnt) {
                    1 -> heartIconId = R.drawable.heart_icon_disabled
                    2 -> heartIconId = R.drawable.hearticon
                    else -> {
                        iconClicksCnt = 1
                        heartIconId = R.drawable.heart_icon_disabled
                    }
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

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MakeProductCard2(navController: NavController, product: ProductShort?) {
    val productWeight by remember { mutableStateOf(VariantType.FIFTY_GRAMS) }
    var iconClicksCnt by remember {
        mutableIntStateOf(1)
    }
    var expanded by remember{ mutableStateOf(false) }
    var heartIconId: Int
    val dropMenuWidth = 120
    if(product != null) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                .fillMaxWidth()
                .background(White10)
                .clickable(onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
                    navController.navigate("product_screen/$product")
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
                        .height(200.dp)
                        .widthIn(0.dp, 200.dp),
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
                ) {
                    Text(
                        text = product.title,
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10
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
                            text = "${product.countOfReviews} отзывов",
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
                            text = "${product.packages[0].price*product.discount.toDouble()} ₽",
                            fontFamily = montserratFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Text(
                            text = "${product.packages[0].price} ₽",
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
                            DropDownMenu(
                                dropMenuWidth = dropMenuWidth,
                                expanded = expanded,
                                expandedChange = {expanded = it}
                            )
                        }
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
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
                        onClick = { iconClicksCnt++ }
                    )
                    .padding(5.dp)
            ) {
                when (iconClicksCnt) {
                    1 -> heartIconId = R.drawable.heart_icon_disabled
                    2 -> heartIconId = R.drawable.hearticon
                    else -> {
                        iconClicksCnt = 1
                        heartIconId = R.drawable.heart_icon_disabled
                    }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
    }
}


