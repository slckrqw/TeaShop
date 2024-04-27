package com.example.teashop.screen.screen.profile_screen.user_data

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.teashop.R
import com.example.teashop.data.model.saves.UserSave
import com.example.teashop.data.model.user.User
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.MakeFullTextField
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchUserDataScreen(navController: NavController, user: User?){
    user?.let { item ->
        Navigation(navController = navController){
            MakeUserDataScreen(navController = navController, user = item)
        }
    }
}

@Composable
fun MakeUserDataScreen(navController: NavController, user: User){
    val context: Context = LocalContext.current
    val tokenStorage = TokenStorage()
    val userDataViewModel: UserDataViewModel = viewModel()
    var userName by remember {
        mutableStateOf(user.name)
    }
    var userSurname by remember {
        mutableStateOf(user.surname)
    }
    var userEmail by remember{
        mutableStateOf(user.email)
    }
    var userFirstPassword by remember{
        mutableStateOf("")
    }
    var userPassword by remember{
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            MakeTopCard(drawableId = R.drawable.back_arrow, text = "Мои данные", navController = navController)
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Персональные данные",
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                        color = Black10,
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                    )
                    MakeFullTextField(header = "Имя", onValueChange = { userName = it }, inputValue = user.name)
                    MakeFullTextField(header = "Фамилия", onValueChange = { userSurname = it }, bottomPadding = 0, inputValue = user.surname)
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = White10)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Учётные данные",
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                        color = Black10,
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                    )
                    MakeFullTextField(header = "Email", onValueChange = { userEmail = it }, inputValue = user.email)
                    MakeFullTextField(header = "Пароль", onValueChange = { userFirstPassword = it })
                    MakeFullTextField(header = "Новый пароль", onValueChange = { userPassword = it }, bottomPadding = 0)
                }
            }
        }
        MakeAgreeBottomButton(
            onClick = {
                if (userName.isEmpty() || userSurname.isNullOrEmpty() || userEmail.isEmpty()) {
                    makeToast(context,"Заполните все данные корректно")
                    return@MakeAgreeBottomButton
                }
                if (!userEmail.contains("@")) {
                    makeToast(context,"Укажите корректный адрес электронной почты")
                    return@MakeAgreeBottomButton
                }
                if ((userPassword.isNotEmpty() && userFirstPassword.isNotEmpty() && userFirstPassword != userPassword) ||
                    (userPassword.isNotEmpty() && userFirstPassword.isEmpty()) ||
                    (userPassword.isEmpty() && userFirstPassword.isNotEmpty())) {
                    makeToast(context,"Пароли должны совпадать")
                    return@MakeAgreeBottomButton
                }

                val userSave = UserSave(
                    userName.trim(),
                    userSurname!!.trim(),
                    userEmail.trim(),
                    if (userPassword.trim().isEmpty()) userPassword.replace(" ", "")
                    else null,
                )

                userDataViewModel.saveUserInfo(
                    tokenStorage.getToken(context),
                    userSave,
                    onSuccess = {
                        tokenStorage.deleteToken(context)
                        makeToast(context, "Данные успешно обновлены!")
                        navController.navigate(
                            Screen.Log.route,
                            navOptions = navOptions {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        )
                    },
                    onError = {
                        makeToast(context, "Укажите корректные данные")
                    }
                )
            },
            text = "Сохранить изменения")
    }
}

private fun makeToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewUserData() {
    TeaShopTheme {
    }
}