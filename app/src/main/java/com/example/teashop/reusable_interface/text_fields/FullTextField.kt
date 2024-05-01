package com.example.teashop.reusable_interface.text_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun MakeFullTextField(
    header: String,
    onValueChange: (String) -> Unit = {},
    bottomPadding: Int = 10,
    inputValue: String? = ""
){
    var value by remember{
        mutableStateOf(inputValue ?: "")
    }
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
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
            modifier = Modifier
                .padding(bottom = bottomPadding.dp)
                .fillMaxWidth(),
            singleLine = true
        )
    }
    onValueChange(value)
}