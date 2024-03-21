package com.example.teashop.search_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.category_screen.CategoryScreen
import com.example.teashop.reusable_interface.SearchCard
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
import com.example.teashop.data.DataSource
const val boxesHeight = 170

class SearchScreen {
    @Composable
    fun ColumnSearchScreen(){
        var catalogScreen by remember{mutableIntStateOf(-1)}
        when(catalogScreen) {
            -1 -> {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                    SearchCard().MakeCard()
                    NewProductsBanner(catalogChange = {catalogScreen = it})
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TeaProductsBanner(R.drawable.tea_production, R.string.teaProduction, catalogChange = {catalogScreen = it},2)
                        TeaProductsBanner(R.drawable.tea_dishes, R.string.teaDishes, catalogChange = {catalogScreen = it}, 3)
                    }
                    PopularProductsBanner(catalogChange = {catalogScreen = it})
                }
            }
            1-> { CatalogScreen(backScreen = {catalogScreen = it}, topNameId = R.string.newTextCatalog)
                .MakeCatalogScreen(productsList = DataSource().loadProducts())}
            2-> {CategoryScreen().MakeCategoryScreen(
                DataSource().loadCategories(),
                screenBack = {catalogScreen = it},
                nameId = R.string.categoryTeaGoodsName
            )}
            3-> {CategoryScreen().MakeCategoryScreen(
                DataSource().loadCategories(),
                screenBack = {catalogScreen = it},
                nameId = R.string.categoryTeaDishesName
            )}
            4->{
                CatalogScreen(backScreen = {catalogScreen = it}, topNameId = R.string.popularTextCatalog)
                    .MakeCatalogScreen(productsList = DataSource().loadProducts())
            }
        }
    }
    @Composable
    fun NewProductsBanner(catalogChange:(Int)->Unit){
        Box(modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(boxesHeight.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painterResource(id = R.drawable.new_products_search),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .clickable(onClick = {catalogChange(1)})
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 15.dp, bottom = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.newProductsCardSearchScreen1),
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = White10
                )
                Text(
                    text = stringResource(R.string.newProductsCardSearchScreen2),
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W500,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    color = White10
                )
            }

        }
    }

    @Composable
    fun RowScope.TeaProductsBanner(drawableId: Int, stringId: Int, catalogChange:(Int)->Unit, screenId: Int){
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .height(boxesHeight.dp)
                .width(150.dp)
                .weight(1f)
        ){
            Image(
                painterResource(drawableId),
                contentDescription = null,
                contentScale = ContentScale.Crop,//TODO change image scale
                modifier = Modifier
                    .clickable(onClick = {catalogChange(screenId)})
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxSize()
            )
            Text(
                text = stringResource(id = stringId),
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W700,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                color = White10,
                modifier = Modifier.padding(top = 15.dp)
            )
        }
    }

    @Composable
    fun PopularProductsBanner(catalogChange:(Int)->Unit){
        Box(modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(boxesHeight.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painterResource(id = R.drawable.popular_products_search),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable(onClick = { catalogChange(4) })
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxSize()
            )
            Text(
                text = stringResource(R.string.popularGoodsSearchScreen),
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W700,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                color = White10,
                modifier = Modifier.padding(start = 25.dp, bottom = 20.dp)
            )
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TeaShopTheme {
            ColumnSearchScreen()
        }
    }
}
