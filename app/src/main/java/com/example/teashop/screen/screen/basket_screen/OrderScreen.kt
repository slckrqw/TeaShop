package com.example.teashop.screen.screen.basket_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchOrderScreen(navController: NavController){
    Navigation(navController = navController) {
        MakeOrderScreen(
            navController = navController
        )
    }
}

@Composable
fun MakeOrderScreen(navController: NavController){

    var productCnt = 1
    var basketList = DataSource().loadProducts()
    var receiverName by remember {
        mutableStateOf("")
    }
    var receiverSurName by remember {
        mutableStateOf("")
    }
    var receiverEmail by remember {
        mutableStateOf("")
    }
    var receiverPhone by remember {
        mutableStateOf("")
    }

    var writeOffBonus by remember {
        mutableStateOf(false)
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ){
        item{
            MakeTopCard(drawableId = R.drawable.back_arrow, textId = R.string.BasketOrder, navController = navController)
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
                ReceiverTextField(header = "Имя", onValueChange = {receiverName = it})
                ReceiverTextField(header = "Фамилия", onValueChange = {receiverSurName = it})
                ReceiverTextField(header = "Email", onValueChange = {receiverEmail = it})
                ReceiverTextField(header = "Номер телефона", onValueChange = {receiverPhone = it})
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
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier.fillMaxWidth()
            ){
                Column(
                    modifier = Modifier.padding(10.dp)
                ){
                    Text(
                        text = "Сумма заказа",
                        fontFamily = montserratFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    SumTextRow(header = "Количество товаров", answer = "$productCnt шт.", textColor = Black10)
                    SumTextRow(header = "Начислится бонусы", answer = "+${basketList[productCnt].bonusCnt}", textColor = Green10)
                    SumTextRow(header = "Списание бонусов", answer = "-0", textColor = Red10)

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Итог",
                            fontFamily = montserratFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W600,
                            color = Black10,
                        )
                        Text(
                            text = "${basketList[productCnt].price} руб."
                        )
                    }
                }
            }
        }
        item{
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Green10),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Оформить заказ",
                    fontFamily = montserratFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    color = White10
                )
            }
        }
    }
}

@Composable
fun ReceiverTextField(header: String, onValueChange: (String) -> Unit){
    var value by remember{
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(start = 10.dp)
    ) {
        Text(
            text = header,
            fontFamily = montserratFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.W400,
            color = Black10
        )
        TextField(
            value = value,
            onValueChange = { value = it },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = White10,
                unfocusedIndicatorColor = White10,
                focusedContainerColor = Grey20,
                unfocusedContainerColor = Grey20,
                disabledContainerColor = Black10,
                disabledTextColor = Black10,
                focusedTextColor = Black10,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier
                .padding(end = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            singleLine = true
        )
    }
    onValueChange(value)
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