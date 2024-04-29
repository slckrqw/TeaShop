package com.example.teashop.screen.screen.basket_screen.order

import android.content.Context
import android.widget.Toast.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.teashop.R
import com.example.teashop.data.model.address.Address
import com.example.teashop.data.model.bucket.Bucket
import com.example.teashop.data.model.user.User
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.MakeFullTextField
import com.example.teashop.reusable_interface.cards.MakeSummaryCard
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchOrderScreen(
    navController: NavController,
    bucket: Bucket?,
    viewModel: OrderViewModel = viewModel()
){
    val addressView by viewModel.addresses.observeAsState()
    val userView by viewModel.user.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        val token = tokenStorage.getToken(context)
        token?.let {
            viewModel.getUserAddresses(
                token,
                onError = {
                    makeText(context, "Невозможно получить ваши адреса", LENGTH_SHORT).show()
                }
            )

            viewModel.getLoggedUserInfo(
                it,
                onError = {
                    makeText(context, "Упс, вы не еще не авторизировались", LENGTH_SHORT).show()
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
    }
    if (addressView != null && bucket != null && userView != null) {
        Navigation(navController = navController) {
            MakeOrderScreen(
                navController,
                addressView!!,
                viewModel,
                bucket,
                userView!!,
                tokenStorage,
                context
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MakeOrderScreen(
    navController: NavController,
    addressList: MutableList<Address>,
    viewModel: OrderViewModel,
    bucket: Bucket,
    user: User,
    tokenStorage: TokenStorage,
    context: Context
){
    val totalCount = remember {
        bucket.products?.sumOf {
            it.quantityInBucket
        }
    }
    var receiverName by remember {
        mutableStateOf(user.name)
    }
    var receiverSurName by remember {
        mutableStateOf(user.surname)
    }
    var receiverEmail by remember {
        mutableStateOf(user.email)
    }
    var receiverPhone by remember {
        mutableStateOf("")
    }
    var writeOffBonus by remember {
        mutableStateOf(false)
    }
    var selectedAddress by remember {
        mutableIntStateOf(0)
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ){
        item{
            MakeTopCard(drawableId = R.drawable.back_arrow, text = "Оформление заказа", navController = navController)
        }
        item{
            Column(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(
                    text = "Выберите адрес доставки",
                    fontFamily = montserratFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Black10,
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 10.dp)
                )
                Card(
                    colors = CardDefaults.cardColors(containerColor = White10),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                navController.navigate(Screen.AddressChange.route)
                            }
                        )
                ){
                    Row(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(R.drawable.category_example),
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(30.dp),
                            tint = Green10,
                            contentDescription = null
                        )
                        Text(
                            text = "Изменить текущий адрес",
                            fontFamily = montserratFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W500,
                            color = Black10,
                        )
                    }
                }
            }
        }
        items(addressList.size, { addressList[it].id }){address ->
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            selectedAddress = address
                        },
                        onLongClick = {
                            tokenStorage.getToken(context)?.let {
                                viewModel.deleteAddress(
                                    it,
                                    addressList[address].id,
                                    onSuccess = {
                                        makeText(context, "Адрес удален", LENGTH_SHORT).show()
                                    },
                                    onError = {
                                        makeText(
                                            context,
                                            "Не удалось удалить адрес",
                                            LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        }
                    )
            ){
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.address_icon),
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(30.dp),
                        tint = when(address == selectedAddress)
                        {
                            true -> Green10
                            false -> Grey10
                        },
                        contentDescription = null
                    )
                    Text(
                        text = addressList[address].address,
                        fontFamily = montserratFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10,
                    )
                }
            }
        }
        item{
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                shape = RoundedCornerShape(10.dp)
            ){
                Text(
                    text = "Получатель",
                    fontFamily = montserratFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W500,
                    color = Black10,
                    modifier = Modifier.padding(10.dp)
                )
                MakeFullTextField(
                    header = "Имя",
                    inputValue = receiverName,
                    onValueChange = {receiverName = it}
                )
                MakeFullTextField(
                    header = "Фамилия",
                    inputValue = receiverSurName,
                    onValueChange = {receiverSurName = it}
                )
                MakeFullTextField(
                    header = "Email",
                    inputValue = receiverEmail,
                    onValueChange = {receiverEmail = it}
                )
                MakeFullTextField(
                    header = "Номер телефона",
                    inputValue = receiverPhone,
                    onValueChange = {receiverPhone = it}
                )
            }
        }
        item {
            Row(
                modifier = Modifier.padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Switch(
                    checked = writeOffBonus,
                    onCheckedChange = { writeOffBonus = it },
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = White10,
                        uncheckedBorderColor = White10,
                        checkedThumbColor = Green10,
                        uncheckedThumbColor = Grey10,
                        checkedTrackColor = Grey20,
                        uncheckedTrackColor = Grey20,
                    )
                )
                Text(
                    text = "Списать все бонусы?",
                    fontFamily = montserratFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = Black10,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
        item{
            MakeSummaryCard(
                productCnt = totalCount ?: 0,
                bonusesSpent = if (writeOffBonus) user.teaBonuses else 0,
                bonusesAccrued = bucket.plusTeaBonuses,
                totalCost = if (writeOffBonus) bucket.totalSumWithDiscount - user.teaBonuses else bucket.totalSumWithDiscount
            )
        }
        item{
            MakeAgreeBottomButton(onClick = { /*TODO*/ }, text = "Оформить заказ")
        }
    }
}

@Composable
fun SumTextRow(header: String, answer: String, textColor: Color){
    Row(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = header,
            fontFamily = montserratFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.W400,
            color = Black10
        )
        Text(
            text = answer,
            fontFamily = montserratFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.W400,
            color = textColor
        )
    }
}