package com.example.teashop.basket_screen

import androidx.compose.runtime.Composable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.data.Feedback
import com.example.teashop.data.Product
import com.example.teashop.feedback_screen.FeedbackScreen
import com.example.teashop.reusable_interface.SimpleTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

class BasketScreen {
    @Composable
    fun MakeBasketScreen(){
        var basketListTest = remember {
            mutableStateListOf<Product>()
        }
        var basketList = DataSource().loadProducts()
        var productCnt = 1
        var basketScreenChange by remember{ mutableIntStateOf(-1) }

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

        when(basketScreenChange) {
            -2 -> LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ){
                item{
                    SimpleTopCard(backScreen = {basketScreenChange = it})
                        .MakeTopCard(drawableId = R.drawable.back_arrow, textId = R.string.BasketOrder)
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
            -1 -> Column(
            ) {
                SimpleTopCard(backScreen = {}).MakeTopCard(drawableId = R.drawable.back_arrow, textId = R.string.Basket, iconSwitch = false)
                if (basketList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Корзина пуста",
                                    fontFamily = montserratFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    color = Black10
                                )
                                Text(
                                    text = "Вы ещё не добавили товары в корзину",
                                    fontFamily = montserratFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W500,
                                    color = Grey10
                                )
                                Button(
                                    onClick = { /*TODO*/ },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Green10)
                                ) {
                                    Text(
                                        text = "Перейти в каталог",
                                        fontFamily = montserratFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W400,
                                        color = White10
                                    )
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn {
                        items(basketList.size) { basketItem ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = White10),
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .height(125.dp)
                                    .fillMaxWidth(),
                            ) {
                                Row(

                                ) {
                                    Image(
                                        painter = painterResource(basketList[basketItem].imageResourceId),
                                        modifier = Modifier
                                            .widthIn(0.dp, 125.dp),
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = null
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Text(
                                            text = stringResource(basketList[basketItem].nameId),
                                            fontFamily = montserratFamily,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.W500,
                                            color = Black10,
                                            modifier = Modifier.padding(bottom = 5.dp)
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 10.dp)
                                        ) {
                                            Text(
                                                text = "${basketList[basketItem].price} ₽ x 50гр",
                                                fontFamily = montserratFamily,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.W700,
                                                color = Black10
                                            )
                                            Text(
                                                text = "+ ${basketList[basketItem].bonusCnt} бонусов",
                                                fontFamily = montserratFamily,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.W400,
                                                color = Green10
                                            )
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Card(
                                                colors = CardDefaults.cardColors(containerColor = Grey20),
                                                modifier = Modifier
                                                    .width(120.dp)

                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 15.dp,
                                                            end = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp
                                                        )
                                                ) {
                                                    BasketIcon(icon = R.drawable.minus_icon)
                                                    Text(
                                                        text = "$productCnt"
                                                    )
                                                    BasketIcon(icon = R.drawable.plus_icon)
                                                }
                                            }
                                            IconButton(
                                                onClick = { /*TODO*/ },
                                                colors = IconButtonDefaults.iconButtonColors(
                                                    containerColor = Grey20
                                                ),
                                                modifier = Modifier.size(30.dp)
                                            ) {
                                                BasketIcon(icon = R.drawable.cross_icon)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            Button(
                                onClick = { basketScreenChange = -2 },
                                colors = ButtonDefaults.buttonColors(containerColor = Green10),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "К оформлению",
                                    fontFamily = montserratFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W400,
                                    color = White10
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun BasketIcon(icon: Int){
        Icon(
            painter = painterResource(icon),
            tint = Green10,
            modifier = Modifier.size(20.dp),
            contentDescription = null
        )
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
        BasketScreen().MakeBasketScreen()
    }
}