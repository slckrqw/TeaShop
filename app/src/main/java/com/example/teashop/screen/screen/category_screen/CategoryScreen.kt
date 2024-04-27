package com.example.teashop.screen.screen.category_screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.category.ParentCategory
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchCategoryScreen(navController: NavController, type: ParentCategory?){
    val viewModel: CategoryViewModel = viewModel()
    val categoryList by viewModel.categoryList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        type?.let {
            viewModel.loadCategories(it)
        }
    }

    val title: String? = remember {
        type?.let {
            when(type) {
                ParentCategory.TEA -> return@let "Чайная продукция:"
                ParentCategory.TEA_DISHES -> return@let "Посуда для чая:"
            }
        }
    }

    Navigation(navController = navController) {
        MakeCategoryScreen(categoryList = categoryList, navController, title)
    }
}

@Composable
fun MakeCategoryScreen(categoryList: List<Category>?, navController: NavController, title: String?) {
    categoryList?.let {
        Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = title,
                navController = navController
            )
            LazyColumn {
                items(categoryList.size, { categoryList[it].id }) { category ->
                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                            .background(White10)
                            .clickable(onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set("category", categoryList[category])
                                navController.navigate("catalog_screen/${category}")
                            })
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(categoryList[category].image.imageUrl),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp)
                                    .size(20.dp),
                            )
                            Text(
                                text = categoryList[category].name,
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeaShopTheme {
    }
}
