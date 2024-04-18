package com.example.teashop.screen.screen.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily


@Composable
fun LaunchRegScreen(navController: NavController){
    Navigation(navController = navController){
        MakeSignInScreen(
            navController = navController,
            header = "Регистрация",
            pageName = "Создание аккаунта",
            nameSwitch = true
        )
    }
}

@Composable
fun LaunchLogScreen(navController: NavController){
    Navigation(navController = navController){
        MakeSignInScreen(
            navController = navController,
            header = "Авторизация",
            pageName = "Вход в аккаунт",
            nameSwitch = false
        )
    }
}

@Composable
fun MakeSignInScreen(
    navController: NavController,
    header: String?,
    pageName: String,
    nameSwitch: Boolean,
){
    var buttonText by remember{
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ){
        MakeTopCard(
            drawableId = R.drawable.back_arrow,
            text = header,
            navController = navController,
            iconSwitch = nameSwitch
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxSize()
                .background(White10),
        ) {
            Icon(
                painter = painterResource(R.drawable.authorization_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 55.dp, bottom = 10.dp)
                    .size(50.dp),
                tint = Green10
            )
            Text(
                text = pageName,
                fontFamily = montserratFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                color = Black10,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            buttonText = if(nameSwitch){
                UserField(
                    header = "Имя",
                    onValueChange = {},
                    icon = R.drawable.name_icon
                )
                "Зарегистрироваться"
            } else{
                "Войти в аккаунт"
            }
            UserField(
                header = "Электронная почта",
                onValueChange = {},
                icon = R.drawable.email_icon
            )
            UserField(header = "Пароль", onValueChange = {}, icon = R.drawable.password_icon)
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Green10),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp, bottom = 35.dp, top = 50.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = buttonText,
                    fontFamily = montserratFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    color = White10
                )
            }
            if(!nameSwitch) {
                Text(
                    text = "Аккаунт еще не создан?",
                    fontFamily = montserratFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    color = Grey10,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = { navController.navigate("reg_screen") })
                )
            }
        }
    }
}

@Composable
fun UserField(header: String, onValueChange: (String) -> Unit, icon: Int){
    var value by remember {
        mutableStateOf("")
    }
    TextField(
        value = value,
        onValueChange = { value = it },
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
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Grey10
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 10.dp)
            .fillMaxWidth(),
        singleLine = true
    )
}