package com.example.teashop.admin_screen.products

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.category.ParentCategory
import com.example.teashop.data.model.packages.PackageProduct
import com.example.teashop.data.model.product.ProductAccounting
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.saves.PackageSave
import com.example.teashop.data.model.saves.ProductSave
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.admin.AdminNavigation
import com.example.teashop.reusable_interface.ImageDownloader
import com.example.teashop.reusable_interface.buttons.MakeAgreeBottomButton
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.reusable_interface.text_fields.MakeFullTextField
import com.example.teashop.screen.screen.feedback_screen.uriToMultipartPart
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchAdminProduct(
    navController: NavController,
    id: Long?,
    accounting: ProductAccounting?,
    viewModel: ProductViewModel = viewModel()
){
    val context = LocalContext.current
    val categoryList by viewModel.categoryList.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        viewModel.loadCategories(ParentCategory.ALL)
    }

    if (id != null) {
        val productView by viewModel.product.observeAsState()

        LaunchedEffect(Unit) {
            viewModel.getProductById(
                id,
                onError = {
                    Toast.makeText(
                        context,
                        "Получение списка продуктов временно недоступно",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
        productView?.let { product ->
            AdminNavigation(navController = navController) {
                MakeAdminProduct(
                    product = product,
                    accounting = accounting,
                    categories = categoryList,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    } else {
        AdminNavigation(navController = navController) {
            MakeAdminProduct(
                product = ProductFull(),
                accounting = accounting,
                categories = categoryList,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MakeAdminProduct(
    viewModel: ProductViewModel,
    product: ProductFull,
    accounting: ProductAccounting?,
    categories: List<Category>,
    navController: NavController
){
    val tokenStorage = remember {
        TokenStorage()
    }
    val context = LocalContext.current
    val packages by remember {
        mutableStateOf(product.packages.toMutableStateList())
    }
    val imageList by remember {
        mutableStateOf(product.images.map { Uri.parse(it.imageUrl) }.toMutableStateList())
    }
    var categoryId by remember {
        mutableIntStateOf(product.category.id)
    }
    var categoryText by remember{
        mutableStateOf(product.category.name)
    }
    var expandedCategory by remember{
        mutableStateOf(false)
    }
    var expandedPackage by remember{
        mutableStateOf(false)
    }
    var editSwitch by remember {
        mutableStateOf(false)
    }
    var pack by remember {
        mutableStateOf(PackageProduct())
    }
    var switchOn by remember {
        mutableStateOf(accounting?.active ?: true)
    }

    LazyColumn {
        item{
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = "Товар",
                navController = navController
            )
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
                        inputValue = product.title,
                        contextLength = 50
                    )
                    MakeFullTextField(
                        header = "Артикул",
                        onValueChange = {product.article = it},
                        bottomPadding = 5,
                        inputValue = product.article,
                        contextLength = 50,
                        lettersOn = false
                    )
                    MakeFullTextField(
                        header = "Описание",
                        onValueChange = {product.description = it},
                        bottomPadding = 5,
                        inputValue = product.description,
                        contextLength = 500
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
                                            categoryId = it.id
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
                        header = "Размер скидки (в процентах)",
                        onValueChange = {
                            if(it.isEmpty()){
                                product.discount = 0
                            }else {
                                if (it.toInt() > 99) {
                                    return@MakeFullTextField
                                }
                                product.discount = it.toInt()
                            }
                        },
                        bottomPadding = 0,
                        inputValue = product.discount.toString(),
                        lettersOn = false
                    )
                    Row(
                       verticalAlignment = Alignment.CenterVertically,
                       modifier = Modifier
                           .padding(start = 10.dp)
                           .fillMaxWidth()
                    ) {
                        Text(
                            text = "Активность",
                            fontFamily = montserratFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400,
                            color = Black10,
                            modifier = Modifier.padding(end = 20.dp)
                        )
                        Switch(
                            checked = switchOn,
                            onCheckedChange = {
                                switchOn = !switchOn
                                accounting?.let {
                                    it.active = switchOn
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedBorderColor = White10,
                                uncheckedBorderColor = White10,
                                checkedThumbColor = Green10,
                                uncheckedThumbColor = Grey10,
                                checkedTrackColor = Grey20,
                                uncheckedTrackColor = Grey20,
                            )
                        )
                    }
                }
            }
        }
        item {
            ImageDownloader(imageList)
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
                            onClick = {
                                pack = PackageProduct()
                                editSwitch = true
                                expandedPackage = true
                            },
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
        items(packages.size, { it }){ index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            editSwitch = false
                            pack = packages[index]
                            expandedPackage = true
                        },
                        onLongClick = {
                            packages.removeAt(index)
                        }
                    ),
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
                        text = packages[index].variant.title.value,
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = Black10
                    )
                    Text(
                        text = packages[index].price.toString(),
                        fontFamily = montserratFamily,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                        color = Black10
                    )
                    Text(
                        text = packages[index].quantity.toString(),
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
                onClick = {
                    val title = product.title.trim()
                    val article = product.article.trim()
                    val description = product.description.trim()
                    val category = categoryId
                    val discount = product.discount

                    if (title.isEmpty() || article.isEmpty() || packages.isEmpty() ||
                        description.isEmpty() || category == 0 || imageList.isEmpty()) {
                        Toast.makeText(context, "Заполните всю информации о продукции", Toast.LENGTH_SHORT).show()
                        return@MakeAgreeBottomButton
                    }

                    val parts = imageList.mapIndexed { _, uri ->
                        uriToMultipartPart(context, uri)
                    }
                    tokenStorage.getToken(context)?.let {
                        viewModel.createOrUpdateProduct(
                            parts,
                            it,
                            ProductSave(
                                id = if (product.id == 0L) null else product.id,
                                packages = packages.map { packageProduct ->
                                    PackageSave(
                                        variantId = packageProduct.variant.id, //TODO change to title
                                        quantity = packageProduct.quantity,
                                        price = packageProduct.price
                                    )
                                },
                                categoryId = categoryId,
                                article = article,
                                title = title,
                                description = description,
                                discount = discount,
                                active = accounting?.active ?: true
                            ),
                            onSuccess = {
                                Toast.makeText(context, "Данные успешно сохранены!", Toast.LENGTH_SHORT)
                                    .show()
                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(
                                    context,
                                    "Не удалось совершить операцию с продуктом",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                },
                text = "Сохранить"
            )
        }
    }
    if(expandedPackage){
        PackageEditSheet(editSwitch = editSwitch, pack = pack, packageList = packages) {
            expandedPackage = it
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageEditSheet(
    editSwitch: Boolean,
    pack: PackageProduct,
    packageList: MutableList<PackageProduct>,
    expandedChange: (Boolean) -> Unit
){
    var expanded by remember{
        mutableStateOf(false)
    }
    var priceTemp by remember{
        mutableStateOf(
            when(pack.price){
                0.0 -> ""
                else -> pack.price.toString()
            }
        )
    }
    var quantityTemp by remember{
        mutableStateOf(
            when(pack.quantity){
                0 -> ""
                else -> pack.quantity.toString()
            }
        )
    }

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
                        text = "Тип упаковки",
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
                            if(packageList
                                .none { it1 ->
                                    it1.variant.title == it
                                }
                            ) {
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
                                        pack.variant.title = it
                                    },
                                    modifier = Modifier
                                        .background(Grey20)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
            MakeAgreeBottomButton(
                onClick = {
                    expandedChange(false)
                    if(priceTemp == "" && pack.price == 0.0){
                        pack.price = 0.0
                    }
                    else if(priceTemp != ""){
                        pack.price = priceTemp.toDouble()
                    }
                    if(quantityTemp == "" && pack.quantity == 0) {
                        pack.quantity = 0
                    }
                    else if(quantityTemp != ""){
                        pack.quantity = quantityTemp.toInt()
                    }
                    if(editSwitch){
                        packageList.add(pack)
                    }
                },
                text = "Сохранить"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminProduct() {
    TeaShopTheme {

    }
}