package com.example.teashop.reusable_interface.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.screen.screen.basket_screen.order.SumTextRow
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun MakeSummaryCard(
    productCnt: Int,
    bonusesSpent: Int,
    bonusesAccrued: Int,
    totalCost: Double
){
    Card(
        colors = CardDefaults.cardColors(containerColor = White10),
        shape = RectangleShape,
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Сумма заказа",
                fontFamily = montserratFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.W700,
                color = Black10,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            SumTextRow(
                header = "Количество товаров",
                answer = "$productCnt шт.",
                textColor = Black10
            )
            SumTextRow(
                header = "Начислится бонусов",
                answer = "+$bonusesAccrued",
                textColor = Green10
            )
            SumTextRow(
                header = "Списание бонусов",
                answer = "-$bonusesSpent",
                textColor = Red10
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Итог",
                    fontFamily = montserratFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W600,
                    color = Black10
                )
                Text(
                    text = "$totalCost руб.",
                    fontFamily = montserratFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W600,
                    color = Black10
                )
            }
        }
    }
}