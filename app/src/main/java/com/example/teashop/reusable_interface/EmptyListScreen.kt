package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun MakeEmptyListScreen(type: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$type пока нет :(",
            fontFamily = montserratFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.W500,
            color = Grey10
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewEmptyListScreen() {
    TeaShopTheme {
        MakeEmptyListScreen(type = "Отзывов")
    }
}