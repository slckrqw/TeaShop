package com.example.teashop.category_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teashop.R
import com.example.teashop.data.Category
import com.example.teashop.data.DataSource
import com.example.teashop.reusable_interface.SimpleTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

class CategoryScreen {

    @Composable
    fun MakeCategoryScreen(categoryList: List<Category>, screenBack: (Int) -> Unit, nameId: Int) {
        Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
            SimpleTopCard(screenBack).MakeTopCard(
                drawableId = R.drawable.back_arrow,
                textId = nameId,
            )
            LazyColumn {
                items(categoryList.size) { category ->
                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                            .background(White10)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                painter = painterResource(id = categoryList[category].drawableId),
                                tint = Green10,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .size(20.dp),
                                contentDescription = null
                            )
                            Text(
                                text = categoryList[category].categoryName,
                                fontFamily = montserratFamily,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W500,
                                color = Black10
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
        }
    }
}



