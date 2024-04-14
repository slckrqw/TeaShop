package com.example.teashop.screen.screen.profile_screen

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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.data.Feedback
import com.example.teashop.data.Product
import com.example.teashop.navigation.Navigation
import com.example.teashop.navigation.Screen
import com.example.teashop.reusable_interface.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.Red10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchProfileScreen(navController: NavController){
    Navigation(navController = navController){
        MakeProfileScreen(navController = navController)
    }
}

@Composable
fun MakeProfileScreen(navController: NavController){
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
        ProfileCard(icon = R.drawable.orders_icon, title = "Мои заказы", counter = true, onClick = {navController.navigate(Screen.Orders.route)})
        ProfileCard(icon = R.drawable.favorit_icon, title = "Избранное")
        ProfileCard(icon = R.drawable.user_data_icon, title = "Мои данные")
        ProfileCard(icon = R.drawable.feedback_icon, title = "Мои отзывы")
        ProfileCard(icon = R.drawable.info_profile_icon, title = "О приложении")
        ProfileCard(icon = R.drawable.exit_icon, title = "Выйти из аккаунта")
        ProfileCard(icon = R.drawable.delete_icon, title = "Удалить аккаунт")
        Button(
            onClick = {navController.navigate("log_screen")}
        ){
            Text(
                text = "to authorization"
            )
        }
    }
}

@Composable
fun ProfileCard(icon: Int, title: String, counter: Boolean = false, onClick: () -> Unit = {}){
    Card(
        colors = CardDefaults.cardColors(containerColor = White10),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = {onClick()})
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
    }
}