package com.example.teashop.search_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.data.Product
import com.example.teashop.product_screen.ProductScreen
import com.example.teashop.reusable_interface.ProductCard
import com.example.teashop.reusable_interface.SearchCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

class CatalogScreen(
    private var filterId: Int = 1,
    val backScreen: (Int)-> Unit = {},
    val topNameId: String = ""
) {
    @Composable
    fun MakeCatalogScreen(productsList: List<Product>){
        var screenConfig2 by remember{ mutableIntStateOf(1) }
        var productScreen by remember{ mutableIntStateOf(-1) }
        when(productScreen) {
            -1-> {
                Column {
                    TopCardCatalog(screenChange = { screenConfig2 = it }, screenConfig2)
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        items(productsList.size) { product ->//TODO solve problem of indexes
                            when (screenConfig2) {
                                0 -> {
                                    ProductCard().MakeProductCard2(productScreen={productScreen = it}, product)
                                }

                                1 -> Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    if (product % 2 == 0) {
                                        ProductCard().RowOfCards(
                                            productScreen = { productScreen = it },
                                            productId1 = product,
                                            productId2 = product + 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else ->{
                ProductScreen().MakeProductScreen(product = productsList[productScreen], backScreen = {productScreen = it})
            }
        }
    }

    @Composable
    fun TopCardCatalog(screenChange: (Int) -> Unit = {}, screenTemp: Int){
        var filterSheet by remember{mutableStateOf(false)}
        var sortingSheet by remember{mutableStateOf(false)}
        val filterText:Int = when(filterId){
            1->R.string.sortingTextCatalog1
            2->R.string.sortingTextCatalog2
            3->R.string.sortingTextCatalog3
            4->R.string.sortingTextCatalog4
            else -> {R.string.sortingTextCatalog1}
        }
        var searchSwitch by remember{mutableIntStateOf(1)}
        when(searchSwitch) {
            0 -> SearchCard().MakeCard(searchCardHide = {searchSwitch = it})
            1 -> {
                Card(
                    shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
                    colors = CardDefaults.cardColors(containerColor = Green10),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth()
                        ) {
                            Row {
                                IconsTopCatalog(
                                    drawableId = R.drawable.back_arrow,
                                    25,
                                    0,
                                    screenChange = { backScreen(-1) })
                                Text(
                                    text = topNameId,
                                    fontFamily = montserratFamily,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 20.sp,
                                    color = White10
                                )
                            }
                            Row {
                                IconsTopCatalog(
                                    drawableId = R.drawable.searchicon,
                                    25,
                                    5,
                                    screenChange = { searchSwitch = it })
                                IconButton(
                                    onClick = {
                                              when(screenTemp){
                                                  0-> screenChange(1)
                                                  1-> screenChange(0)
                                                  else -> screenChange(0)
                                              }
                                    },
                                    modifier = Modifier
                                        .padding(start = 5.dp, end = 5.dp)
                                        .size(25.dp)
                                ){
                                    Icon(
                                        painterResource(R.drawable.format_button),
                                        tint = White10,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp, bottom = 10.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconsTopCatalog(
                                    drawableId = R.drawable.sorting_icon,
                                    iconSize = 20,
                                    5
                                )
                                Text(
                                    text = stringResource(id = filterText),
                                    fontFamily = montserratFamily,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 10.sp,
                                    color = White10,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .clickable(onClick = { sortingSheet = true })
                                )
                            }
                            Row {
                                IconsTopCatalog(
                                    drawableId = R.drawable.filter_icon,
                                    iconSize = 20,
                                    5
                                )
                                Text(
                                    text = stringResource(id = R.string.filterTextCatalog),
                                    fontFamily = montserratFamily,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 10.sp,
                                    color = White10,
                                    modifier = Modifier
                                        .padding(end = 5.dp)
                                        .align(Alignment.CenterVertically)
                                        .clickable(onClick = { filterSheet = true })
                                )
                            }
                        }
                    }
                }
                if (sortingSheet) {
                    BottomSheetCatalog(expandedChange = { sortingSheet = it })
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheetCatalog(expandedChange: (Boolean)->Unit){
        ModalBottomSheet(
            onDismissRequest = {expandedChange(false)},
            containerColor = White10,
             modifier = Modifier.padding(bottom = 10.dp)
        ){
            Column(modifier = Modifier.padding(bottom = 15.dp)) {
                Text(
                    text = "Сортировать",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W500,
                    fontSize = 25.sp,
                    color = Black10,
                    modifier = Modifier.padding(start = 5.dp, bottom = 20.dp)
                )
                SheetTextCatalog(1, expandedChange)
                SheetTextCatalog(2, expandedChange)
                SheetTextCatalog(3, expandedChange)
                SheetTextCatalog(4, expandedChange)
            }
        }
    }

    @Composable
    fun SheetTextCatalog(textId: Int, expandedChange: (Boolean)->Unit){
        val stringId: Int = when(textId){
            1->R.string.sortingTextCatalog1
            2->R.string.sortingTextCatalog2
            3->R.string.sortingTextCatalog3
            4->R.string.sortingTextCatalog4
            else -> {R.string.sortingTextCatalog1}
        }
        val cardColor: Color
        val textColor: Color
        if(textId == filterId){
            cardColor = Green10
            textColor = White10
        }
        else {
            cardColor = White10
            textColor = Black10
        }
        Card(
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RectangleShape,
            modifier = Modifier
                .clip(RectangleShape)
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ){
            Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.height(40.dp)) {
                Text(
                    text = stringResource(id = stringId),
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W700,
                    fontSize = 15.sp,
                    color = textColor,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .fillMaxWidth()
                        .clickable(onClick = {
                            filterId = textId
                            expandedChange(false)
                        })
                )
            }
        }
    }

    @Composable
    fun IconsTopCatalog(drawableId: Int, iconSize: Int, startPadding: Int, screenChange: (Int)-> Unit = {}){
        IconButton(
            onClick = {screenChange(0)},
            modifier = Modifier
                .padding(start = startPadding.dp, end = 5.dp)
                .size(iconSize.dp)
        ){
            Icon(
                painterResource(drawableId),
                tint = White10,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
        CatalogScreen().SheetTextCatalog(textId = 1, expandedChange = {})
    }
}