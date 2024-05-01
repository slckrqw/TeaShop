package com.example.teashop.reusable_interface.text_fields

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun RowScope.MakeHalfTextField(
    header: String,
    onValueChange: (String?) -> Unit = {},
    startPadding: Int,
    endPadding: Int
){
    var value by remember{
        mutableStateOf("")
    }
    Card(
        modifier = Modifier
            .padding(start = startPadding.dp, end = endPadding.dp)
            .weight(1f),
        colors = CardDefaults.cardColors(containerColor = White10)
    ) {
        Column {
            Text(
                text = header,
                fontFamily = montserratFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                color = Black10
            )
            TextField(
                value = value,
                onValueChange = { value = it },
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = White10,
                    unfocusedIndicatorColor = White10,
                    focusedContainerColor = Grey20,
                    unfocusedContainerColor = Grey20,
                    disabledContainerColor = Black10,
                    disabledTextColor = Black10,
                    focusedTextColor = Black10,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                singleLine = true
            )
        }
    }
    onValueChange(value)
}

@Composable
fun RowTest(){
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
    ) {
        MakeHalfTextField(header = "header1", startPadding = 10, endPadding = 20, onValueChange = {})
        MakeHalfTextField(header = "header1", startPadding = 20, endPadding = 10, onValueChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewHalf() {
    TeaShopTheme {
        RowTest()
    }
}