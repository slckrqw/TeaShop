package com.example.teashop.screen.screen.profile_screen.sign_in

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.teashop.R
import com.example.teashop.data.model.user.UserRole
import com.example.teashop.data.model.auth.AuthRequest
import com.example.teashop.data.model.auth.RegistrationRequest
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.admin.AdminScreen
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

const val MAX_SIZE = 70

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
    val tokenStorage = TokenStorage()
    val authViewModel: AuthViewModel = viewModel()
    val registrationViewModel: RegistrationViewModel = viewModel()
    val context = LocalContext.current

    var buttonText by remember{
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var userEmail by remember{
        mutableStateOf("")
    }
    var userPassword by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember {
        mutableStateOf(true)
    }
    val icon = when(passwordVisibility){
        true -> R.drawable.eye
        false -> R.drawable.hide
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
                    onValueChange = { userName = it },
                    icon = R.drawable.name_icon,
                    contentLength = 99
                )
                "Зарегистрироваться"
            } else{
                "Войти в аккаунт"
            }
            UserField(
                header = "Электронная почта",
                onValueChange = {userEmail = it},
                icon = R.drawable.email_icon
            )
            TextField(
                value = userPassword,
                onValueChange = {
                    if (userPassword.length < MAX_SIZE)
                        userPassword = it
                },
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
                        text = "Пароль",
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
                                    passwordVisibility = !passwordVisibility
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
                visualTransformation = when(passwordVisibility){
                    false -> VisualTransformation.None
                    true -> PasswordVisualTransformation()
                }
            )
            Button(
                onClick = {
                    if (!userEmail.contains("@")) {
                        Toast.makeText(context, "Укажите корректный адрес электронной почты",
                            Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (nameSwitch){
                        val registrationRequest = RegistrationRequest(
                            userName.trim(),
                            userEmail.replace(" ", ""),
                            userPassword.replace(" ", "")
                        )
                        registrationViewModel.registration(
                            registrationRequest,
                            onSuccess = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Log.route)
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        val authRequest = AuthRequest(
                            userEmail.replace(" ", ""),
                            userPassword.replace(" ", "")
                        )
                        authViewModel.authenticate(
                            authRequest,
                            onSuccess = { authResponse ->
                                tokenStorage.saveTokenAndRole(context, authResponse.token, authResponse.role)
                                if (authResponse.role == UserRole.USER.name) {
                                    navController.navigate(
                                        Screen.Profile.route,
                                        navOptions = navOptions {
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                    )
                                } else {
                                    navController.navigate(
                                        AdminScreen.Orders.route,
                                        navOptions = navOptions {
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                    )
                                }
                            },
                            onError = {
                                Toast.makeText(context, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                },
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
fun UserField(
    header: String,
    onValueChange: (String) -> Unit,
    icon: Int,
    contentLength: Int = 255
){
    var value by remember {
        mutableStateOf("")
    }
    TextField(
        value = value,
        onValueChange = {
            value = it.take(contentLength)
        },
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
    onValueChange(value)
}