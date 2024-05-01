package com.example.teashop.reusable_interface.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmButton(header: String, accountAction: (Boolean) -> Unit = {}, expandedChange: (Boolean) -> Unit = {}){
    AlertDialog(onDismissRequest = { expandedChange(false) }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = White10)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = header,
                    fontFamily = montserratFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = Black10
                )
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Button(
                        onClick = {accountAction(true)},
                        colors = ButtonDefaults.buttonColors(containerColor = Red10),
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Да",
                            fontFamily = montserratFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = White10
                        )
                    }
                    Button(
                        onClick = { expandedChange(false) },
                        colors = ButtonDefaults.buttonColors(containerColor = Green10),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Нет",
                            fontFamily = montserratFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = White10
                        )
                    }
                }
            }
        }
    }
}