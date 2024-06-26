package com.example.teashop.reusable_interface.cards

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
@Composable
fun MakeTopCard(drawableId:Int, text: String?, iconSwitch: Boolean = true, navController: NavController){
    Card(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .height(60.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
        colors = CardDefaults.cardColors(containerColor = Green10)
    ){
        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            if(iconSwitch) {
                Icon(
                    painter = painterResource(drawableId),
                    tint = White10,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(25.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = { navController.popBackStack() }),
                    contentDescription = null
                )
            }
            if(text != null) {
                Text(
                    text = text,
                    fontFamily = montserratFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = White10
                )
            }
        }
    }
}