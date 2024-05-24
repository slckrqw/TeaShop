package com.example.teashop.admin_screen.profile

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.teashop.R
import com.example.teashop.data.model.user.User
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.admin.AdminNavigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.buttons.ConfirmButton
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.reusable_interface.cards.ProfileCard
import com.example.teashop.screen.screen.profile_screen.profile.ProfileViewModel

@Composable
fun LaunchAdminProfile(navController: NavController){

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
        AdminNavigation(navController = navController){
            MakeProfileAdmin(
                userView,
                tokenStorage,
                context,
                navController
            )
        }
    }
}

@Composable
fun MakeProfileAdmin(
    user: User?,
    tokenStorage: TokenStorage,
    context: Context,
    navController: NavController
){

    var expandedExit by remember{
        mutableStateOf(false)
    }
    var exitAccount by remember{
        mutableStateOf(false)
    }

    Column{
        MakeTopCard(
            drawableId = R.drawable.back_arrow,
            text = "Мой профиль",
            navController = navController,
            iconSwitch = false
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
            icon = R.drawable.exit_icon,
            title = "Выйти из аккаунта",
            onClick = {
                expandedExit = true
            }
        )
    }
    if(expandedExit){
        ConfirmButton(
            header = "Выйти из аккаунта?",
            accountAction = { exitAccount = it },
            expandedChange = { expandedExit = it }
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
}