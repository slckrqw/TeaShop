package com.example.teashop.reusable_interface.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun DropDownMenu(
    dropMenuWidth: Int,
    expanded: Boolean,
    expandedChange: (Boolean) -> Unit
){
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expandedChange(false) },
        modifier = Modifier.background(Grey20)
    ) {
        DropdownItem(
            teaWeight = VariantType.FIFTY_GRAMS,
            dropMenuWidth = dropMenuWidth,
            onClick = {expandedChange(false)}
        )
        DropdownItem(
            teaWeight = VariantType.HUNDRED_GRAMS,
            dropMenuWidth = dropMenuWidth,
            onClick = {expandedChange(false)}
        )
        DropdownItem(
            teaWeight = VariantType.TWO_HUNDRED_GRAMS,
            dropMenuWidth = dropMenuWidth,
            onClick = {expandedChange(false)}
        )
        DropdownItem(
            teaWeight = VariantType.FIVE_HUNDRED_GRAMS,
            dropMenuWidth = dropMenuWidth,
            onClick = {expandedChange(false)}
        )
    }
}

@Composable
fun DropdownItem(
    teaWeight: VariantType,
    dropMenuWidth: Int,
    onClick: () -> Unit
){
    DropdownMenuItem(
        text = {
            Text(
                text = teaWeight.value,
                fontFamily = montserratFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.W200,
                color = Black10
            )
        },
        onClick = {
            productWeight = teaWeight
            onClick()
        },
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .width(dropMenuWidth.dp)
            .height(30.dp)
            .background(Grey20)
    )
}