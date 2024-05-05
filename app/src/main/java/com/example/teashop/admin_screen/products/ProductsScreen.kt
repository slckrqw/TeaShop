package com.example.teashop.admin_screen.products

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.enums.SearchSwitch
import com.example.teashop.data.model.pagination.product.ProductFilter
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.pagination.product.ProductSortType
import com.example.teashop.data.model.pagination.product.ProductSorter
import com.example.teashop.data.model.pagination.product.productFilterSaver
import com.example.teashop.data.model.pagination.product.productSorterSaver
import com.example.teashop.data.model.product.ProductAccounting
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.admin.AdminNavigation
import com.example.teashop.navigation.admin.AdminScreen
import com.example.teashop.reusable_interface.MakeEmptyListScreen
import com.example.teashop.reusable_interface.cards.MakeSearchCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchAdminProducts(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
){
    val filterParams by rememberSaveable(stateSaver = productFilterSaver()) {
        mutableStateOf(ProductFilter())
    }
    val sorterParams by rememberSaveable(stateSaver = productSorterSaver()) {
        mutableStateOf(ProductSorter(sortType = ProductSortType.MORE_SALES))
    }
    val productView by viewModel.products.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        tokenStorage.getToken(context)?.let {
            viewModel.getProductsByFilter(
                it,
                ProductPagingRequest(
                    filter = filterParams,
                    sorter = sorterParams
                ),
                onError = {
                    Toast.makeText(
                        context,
                        "Получение списка продуктов временно недоступно",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    productView?.let { products ->
        AdminNavigation(navController = navController) {
            MakeProductsScreen(
                productsList = products,
                navController = navController,
                topName = "Все товары",
                filterParams = filterParams,
                sorterParams = sorterParams,
                context = context,
                viewModel = viewModel
            )
        }
    }
}
@Composable
fun MakeProductsScreen(
    productsList: List<ProductAccounting?>,
    navController: NavController,
    topName: String?,
    filterParams: ProductFilter,
    sorterParams: ProductSorter,
    viewModel: ProductViewModel,
    context: Context
) {
    val lazyListState = remember { LazyListState() }

    Column {
        TopCardCatalog(
            topName,
            navController,
            sorterParams,
            filterParams,
            viewModel,
            context,
            lazyListState
        )
        if(productsList.isEmpty()){
            MakeEmptyListScreen(type = "Товаров")
        }else {
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(productsList.size, key = { index -> productsList[index]?.id ?: index}) { index ->
                    productsList[index]?.let { product ->
                        Card(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .height(70.dp)
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "id", product.id
                                        )
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "accounting", product
                                        )
                                        navController.navigate(AdminScreen.NewProduct.route)
                                    }
                                ),
                            colors = CardDefaults.cardColors(containerColor = White10),
                            shape = RectangleShape
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                            ){
                                Image(
                                    painter = rememberAsyncImagePainter(product.imageUrl),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .size(70.dp)
                                )
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = product.title,
                                        fontFamily = montserratFamily,
                                        fontWeight = FontWeight.W700,
                                        fontSize = 14.sp,
                                        color = Black10,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Всего продаж: ${product.orderCount} шт.",
                                        fontFamily = montserratFamily,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 13.sp,
                                        color = Black10,
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Text(
                                            text = "На складе: ${product.quantity} шт.",
                                            fontFamily = montserratFamily,
                                            fontWeight = FontWeight.W400,
                                            fontSize = 13.sp,
                                            color = Black10
                                        )
                                        if(productsList[index]?.active == true) {
                                            Text(
                                                text = "Активный товар",
                                                fontFamily = montserratFamily,
                                                fontWeight = FontWeight.W400,
                                                fontSize = 10.sp,
                                                color = Green10
                                            )
                                        }else{
                                            Text(
                                                text = "Неактивный товар",
                                                fontFamily = montserratFamily,
                                                fontWeight = FontWeight.W400,
                                                fontSize = 10.sp,
                                                color = Red10
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopCardCatalog(
    topName: String?,
    navController: NavController,
    sorterParams: ProductSorter,
    filterParams: ProductFilter,
    viewModel: ProductViewModel,
    context: Context,
    lazyListState: LazyListState
){
    var filterSheet by remember{ mutableStateOf(false) }
    var sortingSheet by remember{ mutableStateOf(false) }
    val sortingText = when(sorterParams.sortType){
        ProductSortType.MORE_SALES-> "Продажи по убыванию"
        ProductSortType.LESS_SALES-> "Продажи по возрастанию"
        ProductSortType.MORE_QUANTITY-> "Остаток по убыванию"
        ProductSortType.LESS_QUANTITY-> "Остаток по возрастанию"
        else -> "Продажи по убыванию"
    }
    var searchSwitch by remember{ mutableStateOf(SearchSwitch.FILTERS) }
    val tokenStorage = remember {
        TokenStorage()
    }
    when(searchSwitch) {
        SearchSwitch.SEARCH -> MakeSearchCard(searchCardHide = { newSearchSwitch, searchString ->
            searchSwitch = newSearchSwitch
            filterParams.searchString = searchString
            viewModel.getProductsByFilter(
                tokenStorage.getToken(context)!!,
                ProductPagingRequest(
                    filter = filterParams,
                    sorter = sorterParams
                ),
                onError = {
                    Toast.makeText(context, "Получение списка продуктов временно недоступно", Toast.LENGTH_SHORT).show()
                }
            )
        })
        SearchSwitch.FILTERS -> {
            Card(
                shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
                colors = CardDefaults.cardColors(containerColor = Green10),
                modifier = Modifier
                    .padding(bottom = 10.dp)
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
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "id", null
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "accounting", null
                                    )
                                    navController.navigate(AdminScreen.NewProduct.route)
                                },
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .size(25.dp)
                            ){
                                Icon(
                                    painter = painterResource(R.drawable.plus_icon),
                                    tint = White10,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
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
                                text = sortingText,
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
                BottomSortingCatalog(expandedChange = { sortingSheet = it }, filterParams, sorterParams, viewModel, context, lazyListState)
            }
            if (filterSheet) {
                BottomFilterCatalog(expandedChange = {filterSheet = it}, filterParams, sorterParams, viewModel, context, lazyListState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomFilterCatalog(
    expandedChange: (Boolean) -> Unit,
    filterParams: ProductFilter,
    sorterParams: ProductSorter,
    viewModel: ProductViewModel,
    context: Context,
    lazyListState: LazyListState
){
    val tokenStorage = remember {
        TokenStorage()
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    var sorterText by remember {
        mutableStateOf(when(filterParams.isActive){
            true -> "Активный"
            false -> "Неактивный"
            null -> "Любой"
        })
    }

    ModalBottomSheet(
        onDismissRequest = { expandedChange(false) },
        containerColor = White10,
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Статус товара:",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    color = Black10
                )
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .width(180.dp),
                    contentPadding = PaddingValues(0.dp),
                    border = BorderStroke(1.dp, Green10)
                ) {
                    Text(
                        text = sorterText,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 15.sp,
                        color = Black10,
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Grey20)
                    ) {
                        DropDownItem(
                            productCondition = null,
                            filterParams = filterParams,
                            expandedChange = {expanded = it},
                            topBarChange = {sorterText = it}
                        )
                        DropDownItem(
                            productCondition = true,
                            filterParams = filterParams,
                            expandedChange = {expanded = it},
                            topBarChange = {sorterText = it}
                        )
                        DropDownItem(
                            productCondition = false,
                            filterParams = filterParams,
                            expandedChange = {expanded = it},
                            topBarChange = {sorterText = it}
                        )
                    }
                }
            }
            Button(
                onClick = {
                    expandedChange(false)
                    viewModel.getProductsByFilter(
                        tokenStorage.getToken(context)!!,
                        ProductPagingRequest(
                            filter = filterParams,
                            sorter = sorterParams
                        ),
                        onError = {
                            Toast.makeText(context, "Получение списка продуктов временно недоступно", Toast.LENGTH_SHORT).show()
                        }
                    )
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSortingCatalog(
    expandedChange: (Boolean)->Unit,
    filterParams: ProductFilter,
    sorterParams: ProductSorter,
    viewModel: ProductViewModel,
    context: Context,
    lazyListState: LazyListState
){
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
            SheetTextCatalog(ProductSortType.MORE_SALES, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(ProductSortType.LESS_SALES, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(ProductSortType.MORE_QUANTITY, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(ProductSortType.LESS_QUANTITY, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
        }
    }
}

@Composable
fun SheetTextCatalog(
    textType: ProductSortType,
    expandedChange: (Boolean)->Unit,
    filterParams: ProductFilter,
    sorterParams: ProductSorter,
    viewModel: ProductViewModel,
    context: Context,
    lazyListState: LazyListState
){
    val tokenStorage = remember {
        TokenStorage()
    }
    val sorterText = when(textType){
        ProductSortType.MORE_SALES-> "Продажи по убыванию"
        ProductSortType.LESS_SALES-> "Продажи по возрастанию"
        ProductSortType.MORE_QUANTITY-> "Остаток по убыванию"
        ProductSortType.LESS_QUANTITY-> "Остаток по возрастанию"
        else -> "Ошибка"
    }

    val cardColor: Color
    val textColor: Color
    if(textType == sorterParams.sortType){
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
                text = sorterText,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W700,
                fontSize = 15.sp,
                color = textColor,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            sorterParams.sortType = textType
                            expandedChange(false)
                            viewModel.getProductsByFilter(
                                tokenStorage.getToken(context)!!,
                                ProductPagingRequest(
                                    filter = filterParams,
                                    sorter = sorterParams
                                ),
                                onError = {
                                    Toast
                                        .makeText(
                                            context,
                                            "Получение списка продуктов временно недоступно",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            )
                        }
                    )
            )
        }
    }
}

@Composable
fun DropDownItem(
    productCondition: Boolean?,
    filterParams: ProductFilter,
    expandedChange: (Boolean) -> Unit,
    topBarChange: (String) -> Unit
){
    val itemText = when(productCondition){
        true -> "Активный"
        false -> "Неактивный"
        null -> "Любой"
    }
    DropdownMenuItem(
        text = {
            Text(
                text = itemText,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                color = Black10,
            )
        },
        onClick = {
            filterParams.isActive = productCondition
            expandedChange(false)
            topBarChange(itemText)
        },
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .height(30.dp)
            .width(180.dp)
            .background(Grey20)
    )
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