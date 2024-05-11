package com.example.teashop.admin_screen.orders

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import com.example.teashop.data.model.order.OrderStatus
import com.example.teashop.data.model.pagination.order.OrderFilter
import com.example.teashop.data.model.pagination.order.OrderPagingRequest
import com.example.teashop.data.model.pagination.order.OrderSortType
import com.example.teashop.data.model.pagination.order.OrderSorter
import com.example.teashop.data.model.pagination.order.orderFilterSaver
import com.example.teashop.data.model.pagination.order.orderSorterSaver
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.admin.AdminNavigation
import com.example.teashop.navigation.admin.AdminScreen
import com.example.teashop.reusable_interface.cards.MakeOrderCard
import com.example.teashop.screen.screen.catalog_screen.IconsTopCatalog
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun LaunchAdminOrders(
    navController: NavController,
    viewModel: OrderViewModel = viewModel(),
){
    val filterParams by rememberSaveable(stateSaver = orderFilterSaver()) {
        mutableStateOf(OrderFilter())
    }
    filterParams.byCurrentUser = false
    val sorterParams by rememberSaveable(stateSaver = orderSorterSaver()) {
        mutableStateOf(OrderSorter())
    }
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
                    filter = filterParams,
                    sorter = sorterParams
                ),
                onError = {
                    Toast.makeText(context, "Не удалось получить ваши заказы", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
    AdminNavigation(navController = navController) {
        orderView?.let {orders ->
            MakeOrdersScreen(
                orderList = orders,
                navController = navController,
                sorterParams = sorterParams,
                filterParams = filterParams,
                context = context,
                lazyListState = LazyListState(),
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun MakeOrdersScreen(
    orderList: List<OrderShort?>,
    navController: NavController,
    sorterParams: OrderSorter,
    filterParams: OrderFilter,
    viewModel: OrderViewModel,
    context: Context,
    lazyListState: LazyListState
){
    val tokenStorage = remember {
        TokenStorage()
    }
    var filterSheet by remember{ mutableStateOf(false) }
    var sortingSheet by remember{ mutableStateOf(false) }
    val sortingText = when(sorterParams.sortType){
        OrderSortType.NEW->"Сначала новые"
        OrderSortType.OLD->"Сначала старые"
        OrderSortType.EXPENSIVE->"По убыванию стоимости"
        OrderSortType.CHEAP->"По возрастанию стоимости"
    }
    Column {
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
                        modifier = Modifier
                            .size(25.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(
                                onClick = {
                                    if ((filterParams.minPrice ?: 0.0) > (filterParams.maxPrice
                                            ?: 0.0)
                                    ) {
                                        filterParams.minPrice = null
                                        filterParams.maxPrice = null
                                    }
                                    tokenStorage
                                        .getToken(context)
                                        ?.let {
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
                            ),
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
        LazyColumn {
            items(orderList.size, { orderList[it]?.id ?: it }){order ->
                orderList[order]?.let {
                    MakeOrderCard(order = it, navController = navController, AdminScreen.Description.route)
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
    filterParams: OrderFilter,
    sorterParams: OrderSorter,
    viewModel: OrderViewModel,
    context: Context,
    lazyListState: LazyListState
){
    val tokenStorage = remember {
        TokenStorage()
    }
    var expanded by remember{
        mutableStateOf(false)
    }
    var statusBarText by remember {
        mutableStateOf("")
    }
    statusBarText = if(filterParams.status == null){
        "Любой"
    }else{
        filterParams.status!!.value
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
            Text(
                text = "Дата, дд-мм-гг:",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                color = Black10,
                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
            )
            Row(
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                DatePickerItem(
                    placeHolder = "От",
                    filterParams= filterParams,
                    context = context
                )
                DatePickerItem(
                    placeHolder = "До",
                    filterParams= filterParams,
                    context = context
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Статус заказа:",
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    color = Black10
                )
                Button(
                    onClick = {expanded = true},
                    colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(40.dp)
                        .width(180.dp),
                    contentPadding = PaddingValues(0.dp),
                    border = BorderStroke(1.dp, Green10)
                ) {
                    Text(
                        text = filterParams.status?.value?: "Любой",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 15.sp,
                        color = Black10,
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {expanded = false},
                        modifier = Modifier.background(Grey20)
                    ) {
                        DropDownItem(orderStatus = OrderStatus.NEW, filterParams = filterParams) {
                            expanded = it
                        }
                        DropDownItem(orderStatus = OrderStatus.CONFIRMED, filterParams = filterParams) {
                            expanded = it
                        }
                        DropDownItem(orderStatus = OrderStatus.IN_THE_WAY, filterParams = filterParams) {
                            expanded = it
                        }
                        DropDownItem(orderStatus = OrderStatus.COMPLETED, filterParams = filterParams) {
                            expanded = it
                        }
                        DropDownItem(orderStatus = OrderStatus.CANCELED, filterParams = filterParams) {
                            expanded = it
                        }
                        DropDownItem(orderStatus = null, filterParams = filterParams) {
                            expanded = it
                        }
                    }
                }
            }
            Button(
                onClick = {
                    expandedChange(false)
                    if((filterParams.minPrice  ?: 0.0) > (filterParams.maxPrice ?: 0.0)){
                        filterParams.minPrice = null
                        filterParams.maxPrice = null
                    }
                    tokenStorage.getToken(context)?.let {
                        viewModel.getAllOrders(
                            it,
                            OrderPagingRequest(
                                filter = filterParams,
                                sorter = sorterParams
                            ),
                            onError = {
                                Toast.makeText(context, "Получение списка продуктов временно недоступно", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
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
            priceValueField = it
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
    } else if(priceValueField != ""){
        price(priceValueField)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSortingCatalog(
    expandedChange: (Boolean)->Unit,
    filterParams: OrderFilter,
    sorterParams: OrderSorter,
    viewModel: OrderViewModel,
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
            SheetTextCatalog(OrderSortType.NEW, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(OrderSortType.OLD, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(OrderSortType.EXPENSIVE, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
            SheetTextCatalog(OrderSortType.CHEAP, expandedChange, filterParams, sorterParams, viewModel, context, lazyListState)
        }
    }
}

@Composable
fun SheetTextCatalog(
    textType: OrderSortType,
    expandedChange: (Boolean)->Unit,
    filterParams: OrderFilter,
    sorterParams: OrderSorter,
    viewModel: OrderViewModel,
    context: Context,
    lazyListState: LazyListState
){
    val tokenStorage = remember {
        TokenStorage()
    }
    val sortingText = when(textType){
        OrderSortType.NEW->"Сначала новые"
        OrderSortType.OLD->"Сначала старые"
        OrderSortType.EXPENSIVE->"По убыванию стоимости"
        OrderSortType.CHEAP->"По возрастанию стоимости"
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
                text = sortingText,
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
                            tokenStorage
                                .getToken(context)
                                ?.let {
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
                                                    "Получение списка заказов временно недоступно",
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

@Composable
fun DropDownItem(
    orderStatus: OrderStatus?,
    filterParams: OrderFilter,
    expandedChange: (Boolean) -> Unit
){
    DropdownMenuItem(
        text = {
           Text(
               text = orderStatus?.value?: "Любой",
               fontFamily = montserratFamily,
               fontWeight = FontWeight.W400,
               fontSize = 15.sp,
               color = Black10,
           )
        },
        onClick = {
            filterParams.status = orderStatus
            expandedChange(false)
        },
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .height(30.dp)
            .width(180.dp)
            .background(Grey20)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.DatePickerItem(
    placeHolder: String,
    filterParams: OrderFilter,
    context: Context
){
    var expanded by remember{
        mutableStateOf(false)
    }
    val date = rememberDatePickerState()

    Button(
        onClick = {expanded = true},
        colors = ButtonDefaults.buttonColors(containerColor = Grey20),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .height(40.dp)
            .weight(1f),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(1.dp, Green10)
    ) {
        (if(placeHolder == "От"){
            if (filterParams.dateFrom == null) placeHolder
            else filterParams.dateFrom?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } else {
            if (filterParams.dateTo == null) placeHolder
            else filterParams.dateTo?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        })?.let {
            Text(
                text =
                it,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                color = Black10
            )
        }
        if (expanded) {
            DatePickerDialog(
                onDismissRequest = { expanded = false },
                confirmButton = {
                    Button(
                        onClick = {
                            val selectedZonedDateTime = date.selectedDateMillis?.let {
                                Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                            }

                            if (placeHolder == "От") {
                                if (selectedZonedDateTime != null && (filterParams.dateTo == null || selectedZonedDateTime <= filterParams.dateTo)) {
                                    filterParams.dateFrom = selectedZonedDateTime
                                    expanded = false
                                } else {
                                    Toast.makeText(context, "Дата 'От' не может быть больше даты 'До'", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                if (selectedZonedDateTime != null && (filterParams.dateFrom == null || selectedZonedDateTime >= filterParams.dateFrom)) {
                                    filterParams.dateTo = selectedZonedDateTime
                                    expanded = false
                                } else {
                                    Toast.makeText(context, "Дата 'До' не может быть меньше даты 'От'", Toast.LENGTH_SHORT).show()
                                }
                            }
                            expanded = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = White10),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .height(40.dp)
                            .width(180.dp),
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, Green10)
                    ){
                        Text(
                            text = "Применить",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W400,
                            fontSize = 15.sp,
                            color = Black10
                        )
                    }
                },
                colors = DatePickerDefaults.colors(containerColor = White10)
            ){
                DatePicker(
                    state = date,
                    colors = DatePickerDefaults.colors(
                        containerColor = White10,
                        todayContentColor = Black10,
                        todayDateBorderColor = Green10,
                        dayContentColor = Black10,
                        selectedDayContainerColor = Green10,
                        selectedDayContentColor = White10,
                        disabledDayContentColor = Black10,
                        dayInSelectionRangeContainerColor = Grey10,
                        dayInSelectionRangeContentColor = Black10,
                        disabledSelectedDayContainerColor = Green10,
                        disabledSelectedDayContentColor = White10,
                        weekdayContentColor = Black10
                    )
                )
            }
        }
    }
}