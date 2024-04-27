package com.example.teashop.admin_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.model.order.OrderShort
import com.example.teashop.data.model.pagination.order.OrderFilter
import com.example.teashop.data.model.pagination.order.OrderPagingRequest
import com.example.teashop.data.model.pagination.order.OrderSortType
import com.example.teashop.data.model.pagination.order.OrderSorter
import com.example.teashop.data.model.pagination.product.ProductFilter
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.pagination.product.ProductSortType
import com.example.teashop.data.model.pagination.product.ProductSorter
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.reusable_interface.cards.MakeOrderCard
import com.example.teashop.screen.screen.catalog_screen.BottomFilterCatalog
import com.example.teashop.screen.screen.catalog_screen.BottomSortingCatalog
import com.example.teashop.screen.screen.catalog_screen.CatalogScreenViewModel
import com.example.teashop.screen.screen.catalog_screen.IconsTopCatalog
import com.example.teashop.screen.screen.profile_screen.order.OrderViewModel
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily


@Composable
fun LaunchAdminOrders(
    navController: NavController,
    viewModel: OrderViewModel = viewModel(),
    orderSorter: OrderSorter = OrderSorter(),
    orderFilter: OrderFilter = OrderFilter()
){
    val orderView by viewModel.orders.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        tokenStorage.getToken(context)?.let {
            viewModel.getAllOrders(
                it,
                OrderPagingRequest(
                    filter = orderFilter,
                    sorter = orderSorter
                ),
                onError = {
                    Toast.makeText(context, "Не удалось получить ваши заказы", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
    orderView?.let {orders ->
        MakeOrdersScreen(
            orderList = orders,
            navController = navController
        )
    }
}
@Composable
fun MakeOrdersScreen(
    orderList: List<OrderShort?>,
    navController: NavController,
    sorterParams: ProductSorter,
    filterParams: ProductFilter,
    viewModel: com.example.teashop.admin_screen.OrderViewModel,
    context: Context,
    lazyListState: LazyListState
){
    var filterSheet by remember{ mutableStateOf(false) }
    var sortingSheet by remember{ mutableStateOf(false) }
    val sortingText:Int = when(sorterParams.sortType){
        ProductSortType.POPULAR->R.string.sortingTextCatalog1
        ProductSortType.MORE_RATE->R.string.sortingTextCatalog2
        ProductSortType.EXPENSIVE->R.string.sortingTextCatalog3
        ProductSortType.CHEAP->R.string.sortingTextCatalog4
    }
    Column(

    ) {
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
                    Text(
                        text = "Все заказы",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp,
                        color = White10,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.refresh_icon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = White10
                    )
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
                            text = "TODO",
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
        LazyColumn {
            items(orderList.size){order ->
                orderList[order]?.let {
                    MakeOrderCard(order = it, navController = navController)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomFilterCatalog(
    expandedChange: (Boolean) -> Unit,
    filterParams: ProductFilter,
    sorterParams: ProductSorter,
    viewModel: OrderViewModel,
    context: Context,
    lazyListState: LazyListState
){
    val tokenStorage = remember {
        TokenStorage()
    }
    var switchOn by remember{mutableStateOf(false)}
    switchOn = when(filterParams.inStock){
        true -> true
        false -> false
        null -> false
    }

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
                FilterCatalogField(
                    priceValue = filterParams.minPrice ?: 0.0,
                    price = {
                        val inputPrice = it.toDoubleOrNull()
                        if (inputPrice == null) {
                            Toast.makeText(context, "Укажите верный формат цены", Toast.LENGTH_SHORT).show()
                        } else {
                            filterParams.minPrice = inputPrice
                        }
                    },
                    goalString = "От"
                )
                FilterCatalogField(
                    priceValue = filterParams.maxPrice ?: 0.0,
                    price = {
                        val inputPrice = it.toDoubleOrNull()
                        if (inputPrice == null) {
                            Toast.makeText(context, "Укажите верный формат цены", Toast.LENGTH_SHORT).show()
                        } else {
                            filterParams.maxPrice = inputPrice
                        }
                    },
                    goalString = "До"
                )
            }
            Row(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                WeightButton(weight = VariantType.FIFTY_GRAMS, filterParams)
                WeightButton(weight = VariantType.HUNDRED_GRAMS, filterParams)
                WeightButton(weight = VariantType.TWO_HUNDRED_GRAMS, filterParams)
                WeightButton(weight = VariantType.FIVE_HUNDRED_GRAMS, filterParams)
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
                    onCheckedChange = {
                        filterParams.inStock = it
                        switchOn = !switchOn
                    },
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
                    expandedChange(false)
                    if((filterParams.minPrice  ?: 0.0) > (filterParams.maxPrice ?: 0.0)){
                        filterParams.minPrice = null
                        filterParams.maxPrice = null
                    }
                    viewModel.getAllProducts(
                        tokenStorage.getToken(context),
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

@Composable
fun RowScope.WeightButton(weight: VariantType, filterParams: ProductFilter){
    var colorChange by rememberSaveable{ mutableStateOf(false) }
    filterParams.variantTypes?.let {
        colorChange = filterParams.variantTypes!!.contains(weight)
    }
    val buttonColor: Color = when(colorChange){
        true -> Green10
        false -> Grey10
    }
    Button(
        onClick = {
            colorChange = !colorChange
            if(colorChange){
                filterParams.variantTypes?.add(weight)
            }else{
                filterParams.variantTypes?.remove(weight)
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .padding(end = 10.dp)
            .weight(1f)
    ){
        Text(
            text = weight.value,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            color = White10,
        )
    }
}

@Composable
fun RowScope.FilterCatalogField(priceValue: Double, price: (String) -> Unit, goalString: String){
    var priceValueField by remember{
        mutableStateOf(
            when(priceValue){
                0.0 -> ""
                else -> priceValue.toString()
            }
        )
    }

    TextField(
        value = priceValueField,
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
        onValueChange = {
            priceValueField = it.replace("-", "")
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
    if(priceValueField == "" && priceValue == 0.0){
        price("0")
    } else if(priceValueField == "" && priceValue != 0.0){
    } else{
        price(priceValueField)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSortingCatalog(
    expandedChange: (Boolean)->Unit,
    filterParams: ProductFilter,
    sorterParams: ProductSorter,
    viewModel: CatalogScreenViewModel,
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
            SheetTextCatalog(ProductSortType.POPULAR, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(ProductSortType.MORE_RATE, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(ProductSortType.EXPENSIVE, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(ProductSortType.CHEAP, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
        }
    }
}

@Composable
fun SheetTextCatalog(
    textType: OrderSortType,
    expandedChange: (Boolean)->Unit,
    filterParams: OrderFilter,
    sorterParams: OrderSorter,
    viewModel: com.example.teashop.admin_screen.OrderViewModel,
    context: Context,
    lazyListState: LazyListState
){
    val tokenStorage = remember {
        TokenStorage()
    }
    val stringId: Int = when(textType){
        OrderSortType.NEW -> R.string.sortingTextCatalog1
        OrderSortType.OLD -> R.string.sortingTextCatalog2
        OrderSortType.EXPENSIVE->R.string.sortingTextCatalog3
        OrderSortType.CHEAP->R.string.sortingTextCatalog4
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
                text = stringResource(id = stringId),
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
                            tokenStorage.getToken(context)?.let {
                                viewModel.getAllOrders(
                                    it,
                                    OrderPagingRequest(
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
                        }
                    )
            )
        }
    }
}