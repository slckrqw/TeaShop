package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.teashop.data.enums.NaviBarIcons
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10

class NavigationBarIcon(
    private val drawableId: Int
) {
    @Composable
    fun MakeNavigationBarIcon(
        mutableActionChange:()->Unit,
        colorChange: (NaviBarIcons) -> Unit,
        screen: NaviBarIcons,
        iconColor: Boolean
    ){
        IconButton(
            onClick = {
                mutableActionChange()
                colorChange(screen)
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