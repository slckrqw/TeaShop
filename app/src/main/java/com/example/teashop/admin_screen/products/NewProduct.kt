package com.example.teashop.admin_screen.products

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.packages.PackageProduct
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.navigation.admin.AdminNavigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.ImageDownloader
import com.example.teashop.reusable_interface.buttons.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.reusable_interface.text_fields.MakeFullTextField
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchAdminProduct(navController: NavController){
    AdminNavigation(navController = navController) {
        MakeAdminProduct(
            product = ProductFull(),
            categories = listOf(Category()),
            navController = navController
        )
    }
}

@Composable
fun MakeAdminProduct(
    product: ProductFull,
    categories: List<Category>,
    navController: NavController
){

    var categoryText by remember{
        mutableStateOf("Чёрный чай")
    }
    var expandedCategory by remember{
        mutableStateOf(false)
    }
    var expandedPackage by remember{
        mutableStateOf(false)
    }
    var discountTemp = "0"

    LazyColumn {
        item{
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                MakeTopCard(
                    drawableId = R.drawable.back_arrow,
                    text = "Товар",
                    navController = navController
                )
                Icon(
                    painter = painterResource(R.drawable.delete_icon),
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(
                            onClick = {
                                //TODO
                            }
                        )
                        .size(20.dp),
                    tint = White10,
                    contentDescription = null
                )
            }
        }
        item{
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = White10)
            ){
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ){
                    MakeFullTextField(
                        header = "Название товара",
                        onValueChange = {product.title = it},
                        bottomPadding = 5,
                        inputValue = product.title
                    )
                    MakeFullTextField(
                        header = "Артикул",
                        onValueChange = {product.article = it},
                        bottomPadding = 5,
                        inputValue = product.article
                    )
                    MakeFullTextField(
                        header = "Описание",
                        onValueChange = {product.description = it},
                        bottomPadding = 5,
                        inputValue = product.description
                    )
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    ) {
                        Text(
                            text = "Категория",
                            fontFamily = montserratFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400,
                            color = Black10
                        )
                        Button(
                            onClick = {expandedCategory = true},
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .fillMaxWidth()
                                .height(55.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                            border = BorderStroke(1.dp, Green10)
                        ) {
                            Text(
                                text = categoryText,
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.W400,
                                fontSize = 15.sp,
                                color = Black10
                            )
                            DropdownMenu(
                                expanded = expandedCategory,
                                onDismissRequest = { expandedCategory = false },
                                modifier = Modifier
                                    .background(Grey20)
                                    .padding(horizontal = 10.dp)
                                    .fillMaxWidth()
                            ) {
                                categories.forEach {
                                    DropdownMenuItem(
                                        text = {
                                           Text(
                                               text = it.name,
                                               fontFamily = montserratFamily,
                                               fontWeight = FontWeight.W400,
                                               fontSize = 15.sp,
                                               color = Black10,
                                           )
                                        },
                                        onClick = {
                                            expandedCategory = false
                                            categoryText = it.name
                                        },
                                        modifier = Modifier
                                            .background(Grey20)
                                            .padding(horizontal = 10.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                    MakeFullTextField(
                        header = "Размер скидки",
                        onValueChange = {discountTemp = it},
                        bottomPadding = 0,
                        inputValue = product.discount.toString()
                    )
                }
            }
        }
        item {
            ImageDownloader {

            }
        }
        item{
            Card(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White10),
                shape = RectangleShape
            ){
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ){
                    Row(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "Варианты товара",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W400,
                            fontSize = 13.sp,
                            color = Black10
                        )
                        Button(
                            onClick = {expandedPackage = true},
                            modifier = Modifier
                                .width(120.dp)
                                .height(25.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Green10),
                            shape = RoundedCornerShape(5.dp),
                            contentPadding = PaddingValues(0.dp)
                        ){
                            Text(
                                text = "Добавить",
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.W500,
                                fontSize = 10.sp,
                                color = White10
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Тип",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W500,
                            fontSize = 13.sp,
                            color = Black10
                        )
                        Text(
                            text = "Цена за шт.",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W500,
                            fontSize = 13.sp,
                            color = Black10
                        )
                        Text(
                            text = "Остаток",
                            fontFamily = montserratFamily,
                            fontWeight = FontWeight.W500,
                            fontSize = 13.sp,
                            color = Black10
                        )
                    }
                }
            }
        }
        items(product.packages.size){index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White10),
                shape = RectangleShape,
                border = BorderStroke(1.dp, Grey20)
            ){
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.packages[index].variant.title.value,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = Black10
                    )
                    Text(
                        text = product.packages[index].price.toString(),
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = Black10
                    )
                    Text(
                        text = product.packages[index].quantity.toString(),
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = Black10
                    )
                }
            }
        }
        item{
            MakeAgreeBottomButton(
                onClick = { /*TODO*/ },
                text = "Сохранить"
            )
        }
    }
    if(expandedPackage){
        PackageEditSheet(editSwitch = true, pack = PackageProduct()) {
            expandedPackage = it
        }
    }
    product.discount = discountTemp.toInt()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageEditSheet(
    editSwitch: Boolean,
    pack: PackageProduct,
    expandedChange: (Boolean) -> Unit
){

    var expanded by remember{
        mutableStateOf(false)
    }
    var priceTemp = "0"
    var quantityTemp = "0"

    ModalBottomSheet(
        onDismissRequest = {expandedChange(false)},
        containerColor = White10
    ) {
        Column(
            modifier = Modifier.padding(bottom = 50.dp)
        ){
            MakeFullTextField(
                header = "Цена",
                onValueChange = {priceTemp = it},
                bottomPadding = 5,
                inputValue = pack.price.toString()
            )
            MakeFullTextField(
                header = "Количество",
                onValueChange = {quantityTemp = it},
                bottomPadding = 5,
                inputValue = pack.quantity.toString()
            )
            if(editSwitch) {
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                    border = BorderStroke(1.dp, Green10)
                ) {
                    Text(
                        text = pack.variant.title.value,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W400,
                        fontSize = 15.sp,
                        color = Black10
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Grey20)
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                    ){
                        VariantType.entries.toTypedArray().forEach {
                            DropdownMenuItem(
                                text = {
                                   Text(
                                       text = it.value,
                                       fontFamily = montserratFamily,
                                       fontWeight = FontWeight.W400,
                                       fontSize = 15.sp,
                                       color = Black10,
                                   )
                                },
                                onClick = {
                                    expanded = false
                                },
                                modifier = Modifier
                                    .background(Grey20)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
            MakeAgreeBottomButton(
                onClick = {expandedChange(false)},
                text = "Сохранить"
            )
        }
    }
    pack.price = priceTemp.toDouble()
    pack.quantity = quantityTemp.toInt()
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminProduct() {
    TeaShopTheme {

    }
}