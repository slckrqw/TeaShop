package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun MakeAgreeBottomButton(onClick: () -> Unit, text: String){
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Green10),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontFamily = montserratFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.W400,
            color = White10
        )
    }
}