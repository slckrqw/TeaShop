package com.example.teashop.screen.screen.profile_screen.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.teashop.R
import com.example.teashop.data.enums.CatalogConfig
import com.example.teashop.data.model.user.User
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.logic.bonusDeclension
import com.example.teashop.navigation.Navigation
import com.example.teashop.navigation.Screen
import com.example.teashop.screen.screen.main_screen.BottomSheetBonuses
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchProfileScreen(navController: NavController){
    val viewModel: ProfileViewModel = viewModel()
    val userView by viewModel.user.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }

    LaunchedEffect(Unit) {
        viewModel.getLoggedUserInfo(
            tokenStorage.getToken(context)!!,
            onError = {
                Toast.makeText(context, "Упс, вы не еще не авторизировались", Toast.LENGTH_SHORT).show()
                tokenStorage.deleteToken(context)
                navController.navigate(
                    Screen.Log.route,
                    navOptions = navOptions {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                )
            }
        )
    }

    userView?.let {
        Navigation(navController = navController){
            MakeProfileScreen(userView, viewModel, tokenStorage, context, navController)
        }
    }
}

var orderCount = ""

@Composable
fun MakeProfileScreen(user: User?, viewModel: ProfileViewModel, tokenStorage: TokenStorage, context: Context, navController: NavController){
    user?.ordersCount?.let {
        orderCount = it.toString()
    }
    var bonusInfo by remember {
        mutableStateOf(false)
    }
    var expandedExit by remember{
        mutableStateOf(false)
    }
    var expandedDelete by remember{
        mutableStateOf(false)
    }
    var exitAccount by remember{
        mutableStateOf(false)
    }
    var deleteAccount by remember{
        mutableStateOf(false)
    }

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
                        text = user?.name?.let {
                            "Привет, $it"
                        } ?: "Привет",
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
                        .clickable(
                            onClick = {
                                bonusInfo = true
                            }
                        )
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
                        Column {
                            Text(
                                text = "Мой баланс",
                                fontFamily = montserratFamily,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W500,
                                color = Black10
                            )
                            Text(
                                text = user?.teaBonuses?.let {
                                    "$it" + bonusDeclension(it)
                                } ?: "0 Бонусов",
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
        ProfileCard(
            icon = R.drawable.orders_icon,
            title = "Мои заказы",
            counter = true,
            onClick = {navController.navigate(Screen.Orders.route)}
        )
        ProfileCard(
            icon = R.drawable.favorit_icon,
            title = "Избранное",
            onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("config", CatalogConfig.FAVORITE)
                navController.navigate("catalog_screen/${CatalogConfig.FAVORITE}")
            }
        )
        ProfileCard(
            icon = R.drawable.user_data_icon,
            title = "Мои данные",
            onClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                navController.navigate(Screen.UserData.route)
            }
        )
        ProfileCard(
            icon = R.drawable.feedback_icon,
            title = "Мои отзывы",
            onClick = {
                navController.navigate(Screen.UserFeedback.route)
            }
        )
        ProfileCard(icon = R.drawable.info_profile_icon, title = "О приложении")
        ProfileCard(
            icon = R.drawable.exit_icon,
            title = "Выйти из аккаунта",
            onClick = {
                expandedExit = true
            }
        )
        ProfileCard(
            icon = R.drawable.delete_icon,
            title = "Удалить аккаунт",
            onClick = {
                expandedDelete = true
            }
        )
    }
    if(bonusInfo){
        BottomSheetBonuses(header = "Что такое бонусы?", textId = R.string.BonusInfo) {
            bonusInfo = it
        }
    }
    if(expandedExit){
        ConfirmButton(
            header = "Выйти из аккаунта?",
            accountAction = { exitAccount = it },
            expandedChange = { expandedExit = it }
        )
    }
    if(expandedDelete){
        ConfirmButton(
            header = "Удалить аккаунт?",
            accountAction = { deleteAccount = it },
            expandedChange = { expandedDelete = it }
        )
    }
    if(exitAccount){
        tokenStorage.deleteToken(context)
        navController.navigate(
            Screen.Log.route,
            navOptions = navOptions {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        )
        exitAccount = false
    }
    if (deleteAccount) {
        viewModel.deleteAccount(
            tokenStorage.getToken(context) ?: "",
            onSuccess = {
                Toast.makeText(context, "Вы успешно удалили аккаунт", Toast.LENGTH_SHORT).show()
            },
            onError = {
                Toast.makeText(context, "Упс, ошибка", Toast.LENGTH_SHORT).show()
            })
        tokenStorage.deleteToken(context)

        navController.navigate(
            Screen.Log.route,
            navOptions = navOptions {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        )
        deleteAccount = false
    }
}

@Composable
fun ProfileCard(icon: Int, title: String, counter: Boolean = false, onClick: () -> Unit = {}){
    Card(
        colors = CardDefaults.cardColors(containerColor = White10),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = { onClick() })
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
                    text = orderCount,
                    fontFamily = montserratFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = Grey10
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmButton(header: String, accountAction: (Boolean) -> Unit = {}, expandedChange: (Boolean) -> Unit = {}){
    AlertDialog(onDismissRequest = { expandedChange(false) }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = White10)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = header,
                    fontFamily = montserratFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = Black10
                )
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Button(
                        onClick = {accountAction(true)},
                        colors = ButtonDefaults.buttonColors(containerColor = Red10),
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Да",
                            fontFamily = montserratFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = White10
                        )
                    }
                    Button(
                        onClick = { expandedChange(false) },
                        colors = ButtonDefaults.buttonColors(containerColor = Green10),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Нет",
                            fontFamily = montserratFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            color = White10
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
        ConfirmButton(header = "Выйти из аккаунта?")
    }
}