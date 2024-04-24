package com.example.teashop.reusable_interface

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.enums.CatalogConfig
import com.example.teashop.data.enums.ScreenConfig
import com.example.teashop.data.enums.SearchSwitch
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.product.ProductShort
import com.example.teashop.data.model.pagination.product.ProductFilter
import com.example.teashop.data.model.pagination.product.productFilterSaver
import com.example.teashop.data.model.pagination.product.ProductPagingRequest
import com.example.teashop.data.model.pagination.product.ProductSortType
import com.example.teashop.data.model.pagination.product.ProductSorter
import com.example.teashop.data.model.variant.VariantType
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.cards.MakeProductCard2
import com.example.teashop.reusable_interface.cards.MakeSearchCard
import com.example.teashop.reusable_interface.cards.RowOfCards
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun MakeEmptyListScreen(type: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$type пока нет  :(",
            fontFamily = montserratFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.W500,
            color = Grey10
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewEmptyListScreen() {
    TeaShopTheme {
        MakeEmptyListScreen(type = "Отзывов")
    }
}