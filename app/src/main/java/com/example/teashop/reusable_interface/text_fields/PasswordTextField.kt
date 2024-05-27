package com.example.teashop.reusable_interface.text_fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun PasswordTextField(
    header: String,
    onValueChange: (String) -> Unit,
    contextLength: Int = 255,
    passwordVisibility: (Boolean) -> Unit,
    currentPasswordVisibility: Boolean
){

    var value by remember{
        mutableStateOf("")
    }
    val icon = when(currentPasswordVisibility){
        true -> R.drawable.eye
        false -> R.drawable.hide
    }

    TextField(
        value = value,
        onValueChange = {value = it.take(contextLength)},
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Grey10,
            unfocusedIndicatorColor = Grey10,
            focusedContainerColor = White10,
            unfocusedContainerColor = White10,
            disabledContainerColor = White10,
            disabledTextColor = Black10,
            focusedTextColor = Black10,
        ),
        placeholder = {
            Text(
                text = header,
                fontFamily = montserratFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.W400,
                color = Grey10
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.password_icon),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Grey10
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(
                        onClick = {
                            passwordVisibility(!currentPasswordVisibility)
                        }
                    )
                    .size(25.dp),
                tint = Grey10
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 10.dp)
            .fillMaxWidth(),
        singleLine = true,
        visualTransformation = when(currentPasswordVisibility){
            false -> VisualTransformation.None
            true -> PasswordVisualTransformation()
        }
    )
    onValueChange(value)
}