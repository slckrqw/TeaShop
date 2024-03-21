package com.example.teashop.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.DEFAULT_BALANCE
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.data.Product
import com.example.teashop.product_screen.ProductScreen
import com.example.teashop.reusable_interface.ProductCard
import com.example.teashop.reusable_interface.SearchCard
import com.example.teashop.search_screen.CatalogScreen
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

class MainScreen {

    @Composable
    fun LazyColumnMainScreen(productsList: List<Product>){
        var productScreen by remember{ mutableIntStateOf(-1) }
        when(productScreen) {
            -2 -> { CatalogScreen(backScreen = {productScreen = it}, topNameId = R.string.newTextCatalog)
                .MakeCatalogScreen(productsList = DataSource().loadProducts())}
            -1 -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        SearchCard().MakeCard()
                        NewProductsBanner(catalogScreen = {productScreen = it})
                        BonusInfoCard()
                        PopularProductsText()//TODO place text into start position
                    }
                    items(productsList.size) { product ->//TODO solve problem of indexes
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (product % 2 == 0) {
                                ProductCard(
                                    product = productsList[product]
                                ).MakeProductCard(productScreen = { productScreen = it }, product)

                                ProductCard(
                                    product = productsList[product + 1]
                                ).MakeProductCard(productScreen = { productScreen = it }, product+1)
                            }
                        }
                    }
                }
            }
            else -> {
                ProductScreen().MakeProductScreen(product = productsList[productScreen], backScreen = {productScreen = it})
            }
        }
    }

    fun Modifier.clickableWithoutRipple(
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
    fun NewProductsBanner(catalogScreen: (Int) -> Unit) {
        val height = 195.dp
        Box {
            Image(
                painterResource(id = R.drawable.newproducts),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .padding(top = 20.dp, start = 15.dp, end = 15.dp)
                    .clickableWithoutRipple(interactionSource = MutableInteractionSource(),
                        onClick = {catalogScreen(-2)})
                    .clip(RoundedCornerShape(15.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.newProductsCard1),
                    fontSize = 30.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W600,
                    color = White10,
                    modifier = Modifier
                        .padding(start = 5.dp,end=5.dp,top=60.dp)
                )
                Text(
                    text = stringResource(R.string.newProductsCard2),
                    fontSize = 10.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W800,
                    color = White10,
                    modifier = Modifier
                        .padding(start = 55.dp,end=55.dp,top=45.dp,bottom = 30.dp)
                )
            }
        }
    }

    @Composable
    fun BonusInfoCard(){
        val currentBalance by remember{ mutableIntStateOf(DEFAULT_BALANCE) }
        Card(modifier = Modifier
            .background(Grey20)
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            .height(160.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = Green10)
        )
        {
            Box(contentAlignment = Alignment.TopEnd) {

                    Icon(
                        Icons.Filled.Info,
                        contentDescription = null,
                        tint = White10,
                        modifier = Modifier
                            .padding(top = 10.dp,end = 10.dp)
                            .size(20.dp)
                            .clickableWithoutRipple(interactionSource = MutableInteractionSource(),
                                onClick = {}
                            )
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
                        modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp)
                    ) {
                        Button(
                            onClick = {},
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
                            onClick = {},
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
                                text = "${stringResource(R.string.bonusCardBalanceStatus)} $currentBalance",
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
}