package com.example.teashop.screen.screen.profile_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchUserDataScreen(navController: NavController){
    Navigation(navController = navController){
        MakeUserDataScreen(navController = navController)
    }
}

@Composable
fun MakeUserDataScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            MakeTopCard(drawableId = R.drawable.back_arrow, text = "Мои данные", navController = navController)
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Персональные данные",
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                        color = Black10,
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                    )
                    MakeFullTextField(header = "Имя", onValueChange = {})
                    MakeFullTextField(header = "Фамилия", onValueChange = {})
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = White10)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Учётные данные",
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                        color = Black10,
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                    )
                    MakeFullTextField(header = "Email")
                    MakeFullTextField(header = "Пароль")
                    MakeFullTextField(header = "Новый пароль")
                }
            }
        }
        MakeAgreeBottomButton(onClick = {}, text = "Сохранить изменения")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewUserData() {
    TeaShopTheme {
    }
}