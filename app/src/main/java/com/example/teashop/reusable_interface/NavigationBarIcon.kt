package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10

class NavigationBarIcon(
    private val drawableId: Int
) {
    @Composable
    fun MakeNavigationBarIcon(mutableActionChange:(Int)->Unit, screenId: Int, iconColor: Boolean){
        IconButton(
            onClick = {
                mutableActionChange(screenId)
            },
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp, top = 5.dp, bottom = 5.dp)
                .size(30.dp)
        ) {
            if(iconColor) {
                Icon(
                    painterResource(drawableId),
                    tint = Green10,
                    contentDescription = null
                )
            }
            else {
                Icon(
                    painterResource(drawableId),
                    tint = Grey10,
                    contentDescription = null
                )
            }
        }
    }
}