package com.example.teashop.screen.screen.catalog_screen

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.data.ScreenConfig
import com.example.teashop.data.Product
import com.example.teashop.data.ProductFilter
import com.example.teashop.data.SearchSwitch
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.cards.MakeProductCard2
import com.example.teashop.reusable_interface.cards.MakeSearchCard
import com.example.teashop.reusable_interface.cards.RowOfCards
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

var filterId = 1

@Composable
fun LaunchCatalogScreen(navController: NavController, topName: String?){
    Navigation(navController = navController) {
        MakeCatalogScreen(
            productsList = DataSource().loadProducts(),
            navController = navController,
            topName = topName
        )
    }
}
@Composable
fun MakeCatalogScreen(productsList: List<Product>, navController: NavController, topName: String?) {
    var screenConfig by rememberSaveable{ mutableStateOf(ScreenConfig.ROW) }
    Column {
        TopCardCatalog(
            screenChange = { screenConfig = it },
            screenConfig,
            topName,
            navController
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(productsList.size) { product ->//TODO solve problem of indexes
                when (screenConfig) {
                    ScreenConfig.SINGLE -> {
                       MakeProductCard2(
                            navController,
                            productsList[product]
                       )
                    }

                    ScreenConfig.ROW -> Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (product % 2 == 0) {
                            RowOfCards(
                                navController,
                                product1 = productsList[product],
                                product2 = productsList[product + 1]
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopCardCatalog(
    screenChange: (ScreenConfig) -> Unit = {},
    screenTemp: ScreenConfig,
    topName: String?,
    navController: NavController
){
    var filterSheet by remember{mutableStateOf(false)}
    var sortingSheet by remember{mutableStateOf(false)}
    val filterText:Int = when(filterId){
        1->R.string.sortingTextCatalog1
        2->R.string.sortingTextCatalog2
        3->R.string.sortingTextCatalog3
        4->R.string.sortingTextCatalog4
        else -> {R.string.sortingTextCatalog1}
    }
    var searchSwitch by remember{mutableStateOf(SearchSwitch.FILTERS)}
    when(searchSwitch) {
        SearchSwitch.SEARCH -> MakeSearchCard(searchCardHide = {searchSwitch = it})

        SearchSwitch.FILTERS -> {
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
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {navController.popBackStack()},
                                modifier = Modifier
                                    .size(25.dp)
                            ) {
                                Icon(
                                    painterResource(R.drawable.back_arrow),
                                    tint = White10,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Text(
                                text = topName.toString(),
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.W700,
                                fontSize = 20.sp,
                                color = White10,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                        Row {
                            IconsTopCatalog(
                                drawableId = R.drawable.searchicon,
                                25,
                                screenChange = { searchSwitch = it })
                            IconButton(
                                onClick = {
                                          when(screenTemp){
                                              ScreenConfig.SINGLE-> screenChange(ScreenConfig.ROW)
                                              ScreenConfig.ROW-> screenChange(ScreenConfig.SINGLE)
                                              else -> screenChange(ScreenConfig.ROW)
                                          }
                                },
                                modifier = Modifier
                                    .padding(start = 10.dp)
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
                            .padding(start = 20.dp, end = 15.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable(onClick = { sortingSheet = true })
                        ) {
                            IconsTopCatalog(
                                drawableId = R.drawable.sorting_icon,
                                iconSize = 20
                            )
                            Text(
                                text = stringResource(id = filterText),
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.W700,
                                fontSize = 10.sp,
                                color = White10,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 5.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .clickable(onClick = { filterSheet = true })
                        ) {
                            IconsTopCatalog(
                                drawableId = R.drawable.filter_icon,
                                iconSize = 20
                            )
                            Text(
                                text = stringResource(id = R.string.filterTextCatalog),
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.W700,
                                fontSize = 10.sp,
                                color = White10,
                                modifier = Modifier
                                    .padding(start = 5.dp, end = 5.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
            if (sortingSheet) {
                BottomSortingCatalog(expandedChange = { sortingSheet = it })
            }
            var productfilter: ProductFilter?
            if (filterSheet) {
                productfilter = bottomFilterCatalog(expandedChange = {filterSheet = it})
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bottomFilterCatalog(expandedChange: (Boolean) -> Unit):(ProductFilter?){
    val filterRequest = ProductFilter()
    var switchOn by remember{mutableStateOf(false)}
    var pushToBack = false
    ModalBottomSheet(
        onDismissRequest = { expandedChange(false) },
        containerColor = White10,
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Цена, руб.",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                color = Black10,
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, top = 10.dp)
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterCatalogField(price = {
                    if (it != null) {
                        filterRequest.minPrice = it.toDouble()
                    }
                }, "От")
                FilterCatalogField(price = {
                    if (it != null) {
                        filterRequest.minPrice = it.toDouble()
                    }
                }, "До")
            }
            Row(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                WeightButton(weightRequest = { filterRequest.weightList[0] = it }, weight = 50)
                WeightButton(weightRequest = { filterRequest.weightList[1] = it }, weight = 100)
                WeightButton(weightRequest = { filterRequest.weightList[2] = it }, weight = 200)
                WeightButton(weightRequest = { filterRequest.weightList[3] = it }, weight = 500)
            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "В наличии",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    color = Black10,
                    modifier = Modifier.padding(end = 20.dp)
                )
                Switch(
                    checked = switchOn,
                    onCheckedChange = { switchOn = it },
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = White10,
                        uncheckedBorderColor = White10,
                        checkedThumbColor = Green10,
                        uncheckedThumbColor = Grey10,
                        checkedTrackColor = Grey20,
                        uncheckedTrackColor = Grey20,
                    )
                )
            }
            Button(
                onClick = {
                    pushToBack = true
                    expandedChange(false)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Green10),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 50.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Применить",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    color = White10
                )
            }
        }
    }
    return if(pushToBack){
        pushToBack = false
        filterRequest
    } else null
}

@Composable
fun RowScope.WeightButton(weightRequest: (Boolean) -> Unit, weight: Int){
    var colorChange by rememberSaveable{mutableStateOf(false)}
    val buttonColor: Color = when(colorChange){
        true -> Green10
        false -> Grey10
    }
    Button(
        onClick = {
            colorChange = !colorChange
            weightRequest(colorChange)
        },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .padding(end = 10.dp)
            .weight(1f)
    ){
        Text(
            text = "$weight грамм",
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            color = White10,
        )
    }
}

@Composable
fun RowScope.FilterCatalogField(price: (String?) -> Unit, goalString: String){
    var priceValue by remember{mutableStateOf("0")}
    TextField(
        value = priceValue,
        onValueChange = {priceValue = it},
        shape = RoundedCornerShape(15.dp),
        placeholder = {
            Text(
                text = goalString,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                fontSize = 13.sp,
                color = Black10
            )
        },
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .weight(1f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number).copy(imeAction = ImeAction.Done),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Green10,
            unfocusedIndicatorColor = Green10,
            focusedContainerColor = Grey20,
            unfocusedContainerColor = Grey20,
            disabledContainerColor = Grey20,
            disabledTextColor = Black10,
            focusedTextColor = Black10,
            focusedSupportingTextColor = Black10,
            disabledSupportingTextColor = Black10
        ),
        singleLine = true
    )
    price(priceValue)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSortingCatalog(expandedChange: (Boolean)->Unit){
    ModalBottomSheet(
        onDismissRequest = {expandedChange(false)},
        containerColor = White10,
    ){
        Column(modifier = Modifier.padding(bottom = 30.dp)) {
            Text(
                text = "Сортировать",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W500,
                fontSize = 25.sp,
                color = Black10,
                modifier = Modifier.padding(start = 5.dp, bottom = 10.dp)
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
        Box(contentAlignment = Alignment.CenterStart,
            modifier = Modifier.height(40.dp)
        ) {
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
                    }
                    )
            )
        }
    }
}

@Composable
fun IconsTopCatalog(drawableId: Int, iconSize: Int, screenChange: (SearchSwitch) -> Unit = {}){
    IconButton(
        onClick = {screenChange(SearchSwitch.SEARCH)},
        modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
    }
}