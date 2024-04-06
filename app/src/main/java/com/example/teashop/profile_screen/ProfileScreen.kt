package com.example.teashop.profile_screen

import androidx.compose.runtime.Composable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.teashop.R
import com.example.teashop.basket_screen.BasketScreen
import com.example.teashop.data.DataSource
import com.example.teashop.data.Feedback
import com.example.teashop.data.Product
import com.example.teashop.feedback_screen.FeedbackScreen
import com.example.teashop.reusable_interface.SimpleTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

class ProfileScreen {
    @Composable
    fun MakeProfileScreen(){
        var authorization by remember{
            mutableStateOf(-3)
        }
        when(authorization){
            -3 -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Green10),
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    ) {
                        Column(
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 10.dp, bottom = 20.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.user_icon),
                                    tint = White10,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                )
                                Text(
                                    text = "Привет, Керил",
                                    fontFamily = montserratFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W500,
                                    color = White10
                                )
                            }
                            Card(
                                colors = CardDefaults.cardColors(containerColor = White10),
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, bottom = 30.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable(onClick = {})
                                    .shadow(4.dp, shape = RoundedCornerShape(10.dp))
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(5.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(15.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(

                                    ) {
                                        Text(
                                            text = "Мой баланс",
                                            fontFamily = montserratFamily,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.W500,
                                            color = Black10
                                        )
                                        Text(
                                            text = "100 Бонусов",
                                            fontFamily = montserratFamily,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.W500,
                                            color = Black10
                                        )
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.bonus_collect),
                                        tint = Green10,
                                        modifier = Modifier
                                            .padding(end = 15.dp)
                                            .size(40.dp),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                    ProfileCard(icon = R.drawable.orders_icon, title = "Мои заказы", counter = true)
                    ProfileCard(icon = R.drawable.favorit_icon, title = "Избранное")
                    ProfileCard(icon = R.drawable.user_data_icon, title = "Мои данные")
                    ProfileCard(icon = R.drawable.feedback_icon, title = "Мои отзывы")
                    ProfileCard(icon = R.drawable.info_profile_icon, title = "О приложении")
                    ProfileCard(icon = R.drawable.exit_icon, title = "Выйти из аккаунта")
                    ProfileCard(icon = R.drawable.delete_icon, title = "Удалить аккаунт")
                    Button(
                        onClick = {authorization = -1}
                    ){
                        Text(
                            text = "to authorization"
                        )
                    }
                }
            }
            -2 -> SignInPage(
                header = R.string.RegistrationPage,
                pageName = "Создание аккаунта",
                nameSwitch = true,
                registrationSwitch = {authorization = it}
            )
            -1 -> SignInPage(
                header = R.string.AuthorizationPage,
                pageName = "Вход в аккаунт",
                nameSwitch = false,
                registrationSwitch = {authorization = it}
            )
        }
    }

    @Composable
    fun SignInPage(header: Int, pageName: String, nameSwitch: Boolean, registrationSwitch: (Int) -> Unit = {}){
        var buttonText by remember{
            mutableStateOf("")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ){
            SimpleTopCard(
                backScreen = {registrationSwitch(-1)}
            ).MakeTopCard(drawableId = R.drawable.back_arrow, textId = header, iconSwitch = nameSwitch)
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
                            .clickable(onClick = { registrationSwitch(-2) })
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

    @Composable
    fun ProfileCard(icon: Int, title: String, counter: Boolean = false){
        Card(
            colors = CardDefaults.cardColors(containerColor = White10),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = {})
                .shadow(1.dp, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth()
            ) {
                Row {
                    Icon(
                        painter = painterResource(icon),
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(20.dp),
                        tint = Green10,
                        contentDescription = null
                    )
                    Text(
                        text = title,
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10
                    )
                }
                if(counter) {
                    Text(
                        text = "12",
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        color = Grey10
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
        ProfileScreen().MakeProfileScreen()
    }
}