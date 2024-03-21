package com.example.teashop.reusable_interface

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

class SimpleTopCard (
    val backScreen: (Int) -> Unit
){
    @Composable
    fun MakeTopCard(drawableId:Int, textId: Int){
        Card(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .height(60.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
            colors = CardDefaults.cardColors(containerColor = Green10)
        ){
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(drawableId),
                    tint = White10,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .size(20.dp)
                        .clickable(onClick = {backScreen(-1)}),
                    contentDescription = null
                )
                Text(
                    text = stringResource(textId),
                    fontFamily = montserratFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = White10
                )
            }
        }
    }
}