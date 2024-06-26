package com.example.teashop.screen.screen.profile_screen.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.teashop.R
import com.example.teashop.data.api.EMAIL
import com.example.teashop.data.api.EMAIL_PASSWORD
import com.example.teashop.data.enums.CatalogConfig
import com.example.teashop.data.model.user.User
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.data.utils.bonusDeclension
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.buttons.ConfirmButton
import com.example.teashop.reusable_interface.buttons.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.cards.ProfileCard
import com.example.teashop.reusable_interface.text_fields.MakeFullTextField
import com.example.teashop.screen.screen.main_screen.BottomSheetBonuses
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

const val CURRENT_VERSION = "1.0.0"

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

    userView?.let { user ->
        Navigation(navController = navController){
            MakeProfileScreen(
                user,
                viewModel,
                tokenStorage,
                context,
                navController
            )
        }
    }
}

var orderCount = ""

@Composable
fun MakeProfileScreen(
    user: User,
    viewModel: ProfileViewModel,
    tokenStorage: TokenStorage,
    context: Context,
    navController: NavController
){
    user.ordersCount.let {
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
    var appInfo by remember{
        mutableStateOf(false)
    }
    var expandedSupport by remember {
        mutableStateOf(false)
    }
    var messageText by remember {
        mutableStateOf("")
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
            Column {
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
                        text = user.name.let {
                            "Привет, $it"
                        },
                        fontFamily = montserratFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = White10,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                                text = user.teaBonuses.let {
                                    "$it" + bonusDeclension(it)
                                },
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
        ProfileCard(
            icon = R.drawable.info_profile_icon,
            title = "О приложении",
            onClick = {
                appInfo = true
            }
        )
        ProfileCard(
            icon = R.drawable.support_icon,
            title = "Поддержка",
            onClick = {expandedSupport = true}
        )
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
        Text(
            text = "версия $CURRENT_VERSION",
            fontFamily = montserratFamily,
            fontSize = 10.sp,
            color = Grey10,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        )
    }
    if(appInfo){
        AppInfoSheet {
            appInfo = it
        }
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
    if(expandedSupport){
        SupportMessage(
            expanded = {expandedSupport = it},
            onValueChange = {messageText = it},
            messageText = messageText,
            context = context,
            user = user
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoSheet(expanded: (Boolean) -> Unit = {}){
    ModalBottomSheet(
        onDismissRequest = { expanded(false) },
        containerColor = White10
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 60.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mipmap1),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = "Приложение для любителей чая! Мы предлагаем широкий ассортимент чая из лучших плантаций мира, включая черный, зеленый, белый, улун и эксклюзивные сорта. В нашем приложении вы найдете чай на любой вкус и настроение!",
                fontSize = 20.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W400,
                color = Black10
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportMessage(
    expanded: (Boolean) -> Unit,
    onValueChange: (String) -> Unit = {},
    messageText: String = "",
    context: Context,
    user: User
){
    ModalBottomSheet(
        onDismissRequest = { expanded(false) },
        containerColor = White10
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            MakeFullTextField(
                header = "Опишите проблему",
                bottomPadding = 0,
                inputValue = messageText,
                onValueChange = onValueChange
            )
            MakeAgreeBottomButton(
                onClick = {
                    expanded(false)
                    CoroutineScope(Dispatchers.IO).launch {
                        val username = EMAIL
                        val password = EMAIL_PASSWORD

                        val props = Properties()
                        props["mail.smtp.auth"] = "true"
                        props["mail.smtp.starttls.enable"] = "true"
                        props["mail.smtp.host"] = "smtp.yandex.com"
                        props["mail.smtp.port"] = 587

                        val session = Session.getInstance(props, object : Authenticator() {
                            override fun getPasswordAuthentication(): PasswordAuthentication {
                                return PasswordAuthentication(username, password)
                            }
                        })

                        try {
                            val message = MimeMessage(session)
                            message.setFrom(InternetAddress(username))
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username))
                            message.subject = "Обращение от ${user.email}"
                            message.setText(messageText)

                            Transport.send(message)

                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "Обращение успешно отправлено!", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: MessagingException) {
                            e.printStackTrace()
                            Toast.makeText(context, "Упс! Невозможно отправить обращение :(", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                text = "Отправить"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
        AppInfoSheet {

        }
    }
}