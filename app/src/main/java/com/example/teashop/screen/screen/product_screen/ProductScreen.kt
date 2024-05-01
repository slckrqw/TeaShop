package com.example.teashop.screen.screen.product_screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.saves.ProductToBucket
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.data.utils.reviewDeclension
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.screen.screen.main_screen.BottomSheetBonuses
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

@Composable
fun LaunchProductScreen(
    navController: NavController,
    productId: Long?,
    isFavorite: Boolean?,
    viewModel: ProductViewModel = viewModel()
){
    val context = LocalContext.current
    val productView by viewModel.product.observeAsState()

    LaunchedEffect(Unit) {
        productId?.let {
            viewModel.getProductById(
                it
            ) {
                Toast.makeText(context, "Невозможно получить данный продукт", Toast.LENGTH_SHORT)
                    .show()
                navController.navigate(Screen.Main.route)
            }
        }
    }

    productView?.let { product ->
        Navigation(navController = navController) {
            MakeProductScreen(product, navController, viewModel, isFavorite)
        }
    }
}

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

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun MakeProductScreen(
    product: ProductFull,
    navController: NavController,
    productViewModel: ProductViewModel,
    isFavorite: Boolean?
){
    val heartColor: Color
    val heartIcon: Int
    var productWeight by remember {
        mutableStateOf(if ((product.packages[0].variant.title) == VariantType.PACK
        ) {
            VariantType.PACK
        } else {
            VariantType.FIFTY_GRAMS
        })
    }
    if (productWeight == VariantType.FIFTY_GRAMS) {
        if ((product.packages[0].variant.title) == VariantType.PACK) {
            productWeight = VariantType.PACK
        }
    }
    var expanded by remember{ mutableStateOf(false) }
    var favorite by remember {
        mutableStateOf(isFavorite)
    }
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    var bonusInfo by remember{
        mutableStateOf(false)
    }

    when(favorite){
        false -> {
            heartColor = Green10
            heartIcon = R.drawable.heart_icon_disabled
        }
        true -> {
            heartColor = Red10
            heartIcon = R.drawable.hearticon
        }
        else -> {
            heartColor = Green10
            heartIcon = R.drawable.heart_icon_disabled
        }
    }

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
                            painter = rememberAsyncImagePainter(model = product.images[0].imageUrl),
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
                                                    favorite = !favorite!!
                                                },
                                                onError = {
                                                    makeToast(context, "Упс, что-то пошло не так")
                                                }
                                            )
                                        }
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
                        text = "Артикул: ${product.article}",
                        fontFamily = montserratFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.W200,
                        color = Black10,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Text(
                        text = product.title,
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10,
                        modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                                text = "${
                                    BigDecimal(
                                    product.packages
                                        .first { it.variant.title == productWeight ||
                                                it.variant.title == VariantType.PACK }
                                        .price*(1-product.discount.toDouble()/100)
                                ).setScale(2, RoundingMode.HALF_UP)} ₽",
                                fontFamily = montserratFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W700,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${product
                                    .packages
                                    .first { it.variant.title == productWeight ||
                                            it.variant.title == VariantType.PACK}
                                    .price} ₽",
                                fontFamily = montserratFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.W300,
                                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                                modifier = Modifier.padding(start = 10.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
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
                                                teaWeight = it.variant.title,
                                                expandedChange = { expanded = it },
                                                weightChange = { productWeight = it }
                                            )
                                        }
                                    }
                                }
                            }
                            Button(
                                onClick = {
                                    val token = tokenStorage.getToken(context)
                                    if (token == null) {
                                        makeToast(
                                            context,
                                            "Для действия необходимо авторизироваться"
                                        )
                                        navController.navigate(Screen.Log.route)
                                        return@Button
                                    }

                                    val productToBucket = ProductToBucket(
                                        product.packages.first { it.variant.title == productWeight }.id,
                                        1
                                    )

                                    productViewModel.addProductToBucket(
                                        token,
                                        productToBucket,
                                        onSuccess = {
                                            makeToast(
                                                context,
                                                "Товар добавлен в корзину!"
                                            )
                                        },
                                        onError = {
                                            makeToast(
                                                context,
                                                "Упс, что-то пошло не так"
                                            )
                                        }
                                    )
                                },
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
                                    color = White10,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
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
                            text = "+ ${BigDecimal(
                                product.packages
                                    .first{it.variant.title == productWeight}
                                    .price*0.05
                            ).setScale(0,RoundingMode.HALF_UP)} бонусных рублей",
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
                            .size(25.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(
                                onClick = {
                                    bonusInfo = true
                                }
                            ),
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
                            text = "${product.averageRating}",
                            fontFamily = montserratFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Text(
                            text = "${product.countOfReviews}"+
                                    reviewDeclension(product.countOfReviews),
                            fontFamily = montserratFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.W300,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

                    Button(
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
                            navController.navigate(Screen.Feedback.route)
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
    if(bonusInfo){
        BottomSheetBonuses(header = "Что такое бонусы?", textId = R.string.BonusInfo) {
            bonusInfo = it
        }
    }
}

private fun makeToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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