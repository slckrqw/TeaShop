package com.example.teashop.reusable_interface.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.data.Product
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun MakeFeedbackCard(product: Product?, feedback: Int){
    var starsRateCnt = 0
    if(product != null) {
        Card(
            colors = CardDefaults.cardColors(containerColor = White10),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = product.feedBackList[feedback].userId,
                    fontFamily = montserratFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                )
                Row(
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    while (starsRateCnt < product.feedBackList[feedback].rate) {
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            tint = Yellow10,
                            modifier = Modifier.size(15.dp),
                            contentDescription = null
                        )
                        starsRateCnt++
                    }
                }
                starsRateCnt = 0
                Text(
                    text = product.feedBackList[feedback].content,
                    fontFamily = montserratFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Image(
                        painter = painterResource(id = product.feedBackList[feedback].imageResourceId),
                        contentScale = ContentScale.Inside,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    )
                    Image(
                        painter = painterResource(id = product.feedBackList[feedback].imageResourceId),
                        contentScale = ContentScale.Inside,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    )
                    Image(
                        painter = painterResource(id = product.feedBackList[feedback].imageResourceId),
                        contentScale = ContentScale.Inside,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    )
                }
            }
        }
    }
}