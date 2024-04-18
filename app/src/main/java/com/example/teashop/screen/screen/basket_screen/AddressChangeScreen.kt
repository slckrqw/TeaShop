package com.example.teashop.screen.screen.basket_screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.MakeFullTextField
import com.example.teashop.reusable_interface.MakeHalfTextField
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchAddressChangeScreen(navController: NavController){
    Navigation(navController = navController) {
        MakeAddressChangeScreen(navController = navController)
    }
}

@Composable
fun MakeAddressChangeScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(

        ) {
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = "Изменение адреса",
                navController = navController
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Text(
                        text = "Новый адрес",
                        fontFamily = montserratFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10,
                        modifier = Modifier.padding(10.dp)
                    )
                    MakeFullTextField(
                        header = "Адрес: город, улица, дом",
                        onValueChange = {},
                        bottomPadding = 0
                    )
                    RowTextField(header1 = "Квартира", header2 = "Подъезд")
                    RowTextField(header1 = "Этаж", header2 = "Код домофона")
                }
            }
        }
        MakeAgreeBottomButton(onClick = { navController.popBackStack() }, text = "Сохранить изменения")
    }
}

@Composable
fun RowTextField(header1: String, header2: String){
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
    ) {
        MakeHalfTextField(
            header = header1,
            startPadding = 10,
            endPadding = 20,
            onValueChange = {}
        )
        MakeHalfTextField(
            header = header2,
            startPadding = 20,
            endPadding = 10,
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewAddressChange() {
    TeaShopTheme {
    }
}