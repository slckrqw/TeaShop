package com.example.teashop.screen.screen.main_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.teashop.R
import com.example.teashop.data.enums.CatalogConfig
import com.example.teashop.data.model.pagination.product.ProductFilter
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.product.ProductShort
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.Navigation
import com.example.teashop.navigation.Screen
import com.example.teashop.reusable_interface.cards.MakeSearchCard

import com.example.teashop.reusable_interface.cards.RowOfCards
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchMainScreen(navController: NavController, viewModel: MainScreenViewModel = viewModel()){
    val userView by viewModel.user.observeAsState()
    val productView by viewModel.product.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        val token = tokenStorage.getToken(context)
        token?.let {
            viewModel.getLoggedUserInfo(
                it,
                onError = {
                    Toast.makeText(context, "Упс, вы не еще не авторизировались", Toast.LENGTH_SHORT).show()
                    tokenStorage.deleteToken(context)
                    navController.navigate(
                        Screen.Log.route,
                        navOptions = navOptions {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }

        viewModel.getAllPopularProducts(
            token,
            ProductPagingRequest(
                filter = ProductFilter(
                    onlyPopular = false
                )
            ),
            onError = {
                Toast.makeText(context, "Получение списка продуктов временно недоступно", Toast.LENGTH_SHORT).show()
            }
        )
    }

    val bonusCount = userView?.teaBonuses ?: 0

    Navigation(navController = navController) {
        MakeMainScreen(
            productsList = productView,
            navController = navController,
            bonusCount = bonusCount
        )
    }
}

@Composable
fun MakeMainScreen(productsList: List<ProductShort?>?, navController: NavController, bonusCount: Int){
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            MakeSearchCard()
            NewProductsBanner(navController)
            BonusInfoCard(bonusCount)
            PopularProductsText()//TODO place text into start position
        }
        productsList?.let {
            items(it.size, key = { index -> it[index]?.id ?: index }) { index ->
                if (index % 2 == 0) {
                    val product1 = it[index]
                    val product2 = if (index + 1 < it.size) it[index + 1] else null
                    RowOfCards(
                        navController,
                        product1 = product1,
                        product2 = product2
                    )
                }
            }
        }
    }
}

@Composable
fun NewProductsBanner(navController: NavController) {
    val height = 195.dp
    Box(
        modifier = Modifier
            .padding(top = 20.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .height(height)
    ) {
        Image(
            painterResource(id = R.drawable.newproducts),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .clickable(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "config",
                            CatalogConfig.NEW
                        )
                        navController.navigate("catalog_screen/${CatalogConfig.NEW}")
                    }
                )
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.newProductsCard1),
                fontSize = 30.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W600,
                color = White10,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 60.dp)
            )
            Text(
                text = stringResource(R.string.newProductsCard2),
                fontSize = 10.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W800,
                color = White10,
                modifier = Modifier
                    .padding(start = 55.dp, end = 55.dp, top = 45.dp, bottom = 30.dp)
            )
        }
    }
}


@Composable
fun BonusInfoCard(bonusCount: Int){
    var bonusInfo by remember{ mutableStateOf(false)}
    var bonusToSpend by remember {
        mutableStateOf(false)
    }
    var bonusToCollect by remember {
        mutableStateOf(false)
    }
    Card(modifier = Modifier
        .background(Grey20)
        .padding(top = 15.dp, start = 15.dp, end = 15.dp)
        .height(160.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Green10)
    ){
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                Icons.Filled.Info,
                contentDescription = null,
                tint = White10,
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(
                        onClick = {
                            bonusInfo = true
                        }
                    )
                    .size(20.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.bonusCardName),
                    fontSize = 20.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W800,
                    color = White10,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 10.dp)
                        .height(35.dp)
                )
                val buttonHeight = 45.dp
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp)
                ) {
                    Button(
                        onClick = {bonusToSpend = true},
                        colors = ButtonDefaults.buttonColors(containerColor = White10),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp, end = 3.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.bonusCardToSpend),
                            fontSize = 13.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W600,
                            color = Green10
                        )
                    }
                    Button(
                        onClick = {bonusToCollect = true},
                        colors = ButtonDefaults.buttonColors(containerColor = White10),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 3.dp, end = 10.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.bonusCardToTake),
                            fontSize = 13.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W600,
                            color = Green10
                        )
                    }
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = White10),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight)
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${stringResource(R.string.bonusCardBalanceStatus)} $bonusCount",
                            fontSize = 16.sp,
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W800,
                            color = Green10
                        )
                        Icon(
                            painterResource(R.drawable.moneyicon),
                            tint = Green10,
                            modifier = Modifier
                                .padding(start = 10.dp, top = 5.dp),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
    if(bonusInfo){
        BottomSheetBonuses(header = "Что такое бонусы?", textId = R.string.BonusInfo) {
            bonusInfo = it
        }
    }
    if(bonusToSpend){
        BottomSheetBonuses(header = "Как потратить?", textId = R.string.BonusHowToSpend) {
            bonusToSpend = it
        }
    }
    if(bonusToCollect){
        BottomSheetBonuses(header = "Как получить?", textId = R.string.BonusHowToCollect) {
            bonusToCollect = it
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetBonuses(header: String,textId: Int, expandedChange: (Boolean) -> Unit){
    ModalBottomSheet(
        onDismissRequest = { expandedChange(false) }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 30.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = header,
                fontSize = 20.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                color = Green10
            )
            Text(
                text = stringResource(textId),
                fontSize = 20.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                color = Black10
            )
        }
    }
}

@Composable
fun PopularProductsText(){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
        Text(
            text = stringResource(R.string.popularProductsMainScreenText),
            fontSize = 17.sp,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W900,
            color = Green10,
            modifier = Modifier
                .padding(start = 10.dp, top = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
    }
}