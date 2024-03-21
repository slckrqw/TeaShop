package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teashop.R
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.White10

class BottomBar(
    private var mainScreen: Boolean = false,
    private var searchScreen: Boolean = false
) {

    @Composable
    fun MakeNaviBar() {
        Box(contentAlignment = Alignment.Center) {
            BottomAppBar(
                actions = {

                },
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
                containerColor = White10
            )
        }
    }
}