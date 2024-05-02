package com.example.teashop.screen.screen.basket_screen.order

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.teashop.R
import com.example.teashop.data.model.saves.AddressSave
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.reusable_interface.buttons.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.text_fields.MakeFullTextField
import com.example.teashop.reusable_interface.text_fields.MakeHalfTextField
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchAddressChangeScreen(
    navController: NavController,
    viewModel: OrderViewModel = viewModel()
){
    Navigation(navController = navController) {
        MakeAddressChangeScreen(navController, viewModel)
    }
}

@Composable
fun MakeAddressChangeScreen(navController: NavController, viewModel: OrderViewModel){
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }
    var address by remember {
        mutableStateOf("")
    }
    var flat by remember {
        mutableStateOf("")
    }
    var floor by remember {
        mutableStateOf("")
    }
    var entrance by remember {
        mutableStateOf("")
    }
    var intercomCode by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = "Изменение адреса",
                navController = navController
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Text(
                        text = "Новый адрес",
                        fontFamily = montserratFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W500,
                        color = Black10,
                        modifier = Modifier.padding(10.dp)
                    )
                    MakeFullTextField(
                        header = "Адрес: город, улица, дом",
                        onValueChange = { address = it },
                        bottomPadding = 0,
                        contextLength = 200
                    )
                    RowTextField(
                        header1 = "Квартира", onValueChange1 = { flat = it },
                        header2 = "Подъезд", onValueChange2 = { entrance = it })
                    RowTextField(
                        header1 = "Этаж", onValueChange1 = { floor = it },
                        header2 = "Код домофона", onValueChange2 = { intercomCode = it }
                    )
                }
            }
        }
        MakeAgreeBottomButton(onClick = {
            if (address.trim().isEmpty() || flat.trim().isEmpty() || floor.trim().isEmpty() || flat.toShortOrNull() == null) {
                Toast.makeText(context, "Коорректно заполните информацию об адресе", Toast.LENGTH_SHORT).show()
                return@MakeAgreeBottomButton
            }
            tokenStorage.getToken(context)?.let {
                viewModel.createAddress(
                    it,
                    AddressSave(
                        address,
                        address,
                        flat.toShort(),
                        floor.toShortOrNull(),
                        entrance.toShortOrNull(),
                        intercomCode
                    ),
                    onSuccess = {
                        Toast.makeText(context, "Адрес сохранен", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onError = {
                        Toast.makeText(context, "Не удалось сохранить адрес", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }, text = "Сохранить изменения")
    }
}

@Composable
fun RowTextField(
    header1: String,
    onValueChange1: (String) -> Unit = {},
    header2: String,
    onValueChange2: (String) -> Unit = {}
){
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
    ) {
        MakeHalfTextField(
            header = header1,
            startPadding = 10,
            endPadding = 20,
            onValueChange = {
                it?.let {
                    onValueChange1(it)
                }
            }
        )
        MakeHalfTextField(
            header = header2,
            startPadding = 20,
            endPadding = 10,
            onValueChange = {
                it?.let {
                    onValueChange2(it)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewAddressChange() {
    TeaShopTheme {
    }
}