package com.example.teashop.admin_screen.statistics

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.enums.StatisticsSortType
import com.example.teashop.data.model.statistics.Statistics
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.admin.AdminNavigation
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

var sorter = StatisticsSortType.ALL_TIME

@Composable
fun LaunchAdminStatistics(
    navController: NavController,
    viewModel: StatisticsViewModel = viewModel()
){
    val statsView by viewModel.stats.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        tokenStorage.getToken(context)?.let {
            viewModel.getStatisticsByPeriod(
                it,
                StatisticsSortType.ALL_TIME,
                onError = {
                    Toast.makeText(context, "Не удалось получить статистику", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    statsView?.let { stats ->
        AdminNavigation(navController = navController) {
            MakeAdminStatistics(navController, viewModel, stats)
        }
    }
}

@Composable
fun MakeAdminStatistics(
    navController: NavController,
    viewModel: StatisticsViewModel,
    stats: Statistics
){
    var sorterText by remember{
        mutableStateOf("Весь период")
    }
    sorterText = when(sorter){
        StatisticsSortType.ALL_TIME -> StatisticsSortType.ALL_TIME.value
        StatisticsSortType.YEAR -> StatisticsSortType.YEAR.value
        StatisticsSortType.MONTH -> StatisticsSortType.MONTH.value
        StatisticsSortType.WEEK -> StatisticsSortType.WEEK.value
        StatisticsSortType.DAY -> StatisticsSortType.DAY.value
    }
    var expanded by remember{
        mutableStateOf(false)
    }

    LazyColumn{
        item{
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = "Статистика",
                navController = navController,
                iconSwitch = false
            )
        }
        item{
            Button(
                onClick = {
                    expanded = true
                },
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = White10),
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(1.dp, Green10)
            ){
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.graphic_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(20.dp),
                        tint = Green10
                    )
                    Text(
                        text = sorterText,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 13.sp,
                        color = Black10
                    )
                }
            }
        }
        item{
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .shadow(2.dp, RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White10)
            ){
                Column(
                    modifier = Modifier.padding(10.dp)
                ){
                    Text(
                        text = "Продажи по категориям",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 13.sp,
                        color = Black10
                    )
                    PieChart(stats.categoryStatistics)
                }
            }
        }
        item{
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .shadow(2.dp, RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White10)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp)
                ){
                    Text(
                        text = "Количество заказов по дням",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 13.sp,
                        color = Black10
                    )
                    LineChartBase(stats.ordersStatistics)
                }
            }
        }
        item{
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(10.dp))
            ){
                Column(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
                ){
                    Text(
                        text = "Итог",
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W700,
                        fontSize = 13.sp,
                        color = Black10,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    TextRow(header = "Всего заказов: ", data = stats.countOfOrders.toString())
                    TextRow(header = "Продано товаров: ", data = stats.countOfSales.toString())
                    TextRow(header = "Сумма: ", data = stats.totalPrice.toString())
                }
            }
        }
    }
    if(expanded){
        BottomSortingCatalog(
            viewModel
        ) {
            expanded = it
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSortingCatalog(
    viewModel: StatisticsViewModel,
    expandedChange: (Boolean) -> Unit
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
            BottomSheetText(textType = StatisticsSortType.ALL_TIME, expandedChange = expandedChange, viewModel = viewModel)
            BottomSheetText(textType = StatisticsSortType.YEAR, expandedChange = expandedChange, viewModel = viewModel)
            BottomSheetText(textType = StatisticsSortType.MONTH, expandedChange = expandedChange, viewModel = viewModel)
            BottomSheetText(textType = StatisticsSortType.WEEK, expandedChange = expandedChange, viewModel = viewModel)
            BottomSheetText(textType = StatisticsSortType.DAY, expandedChange = expandedChange, viewModel = viewModel)
        }
    }
}

@Composable
fun BottomSheetText(
    viewModel: StatisticsViewModel,
    textType: StatisticsSortType,
    expandedChange: (Boolean) -> Unit
){
    val sorterText = when(textType){
        StatisticsSortType.ALL_TIME -> StatisticsSortType.ALL_TIME.value
        StatisticsSortType.YEAR -> StatisticsSortType.YEAR.value
        StatisticsSortType.MONTH -> StatisticsSortType.MONTH.value
        StatisticsSortType.WEEK -> StatisticsSortType.WEEK.value
        StatisticsSortType.DAY -> StatisticsSortType.DAY.value
    }
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    val cardColor: Color
    val textColor: Color
    if(textType == sorter){
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
                            sorter = textType

                            tokenStorage.getToken(context)?.let {
                                viewModel.getStatisticsByPeriod(
                                    it,
                                    sorter,
                                    onError = {
                                        Toast.makeText(context, "Не удалось получить статистику", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }

                            expandedChange(false)
                        }
                    )
            )
        }
    }
}

@Composable
fun TextRow(header:String, data: String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = header,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W400,
            fontSize = 13.sp,
            color = Black10
        )
        Text(
            text = data,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W600,
            fontSize = 13.sp,
            color = Black10
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminStatistics() {
    TeaShopTheme {
    }
}