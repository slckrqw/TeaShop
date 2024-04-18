package com.example.teashop.screen.screen.feedback_screen

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.Product
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.cards.MakeFeedbackCard
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

private var filter: String = "Новые"

@Composable
fun LaunchFeedbackScreen(navController: NavController, product: Product?){
    Navigation(navController = navController) {
        MakeFeedbackScreen(
            navController,
            product
        )
    }
}
@Composable
fun MakeFeedbackScreen(navController: NavController, product: Product?){

    var starsRateCnt = 0
    var expandedChange by remember{mutableStateOf(false)}

    if(product != null) {
    Column(
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                MakeTopCard(
                    drawableId = R.drawable.back_arrow,
                    text = "Отзывы",
                    navController = navController
                )
                Icon(
                    painter = painterResource(R.drawable.plus_icon),
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "product",
                                product
                            )
                            navController.navigate("newFeedback_screen/$product")
                        })
                        .size(20.dp),
                    tint = White10,
                    contentDescription = null
                )
            }
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White10)
            ) {
                Column(
                    modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 5.dp)
                ) {
                    Text(
                        text = "Общий рейтинг",
                        fontFamily = montserratFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.star),
                            tint = Yellow10,
                            modifier = Modifier.size(30.dp),
                            contentDescription = null
                        )
                        Text(
                            text = product.rate.toString(),
                            fontFamily = montserratFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                        Text(
                            text = "${product.rateCnt} отзывов",
                            fontFamily = montserratFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W300,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = White10),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clickable(onClick = { expandedChange = true })
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sorting_icon),
                        tint = Green10,
                        modifier = Modifier.size(20.dp),
                        contentDescription = null
                    )
                    Text(
                        text = filter,
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            }
            LazyColumn {
                items(product.feedBackList.size) { feedback ->
                    MakeFeedbackCard(product = product, feedback = feedback)
                }
            }
            if (expandedChange) {
                BottomSheetCatalog(expandedChange = { expandedChange = it })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCatalog(expandedChange: (Boolean)->Unit){
    ModalBottomSheet(
        onDismissRequest = {expandedChange(false)},
        containerColor = White10,
        modifier = Modifier.padding(bottom = 10.dp)
    ){
        Column(modifier = Modifier.padding(bottom = 15.dp)) {
            Text(
                text = "Сортировать",
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W500,
                fontSize = 25.sp,
                color = Black10,
                modifier = Modifier.padding(start = 5.dp, bottom = 20.dp)
            )
            SheetTextCatalog("Новые", expandedChange)
            SheetTextCatalog("Старые", expandedChange)
            SheetTextCatalog("Отрицательные", expandedChange)
            SheetTextCatalog("Положительные", expandedChange)
        }
    }
}

@Composable
fun SheetTextCatalog(text: String, expandedChange: (Boolean)->Unit){

    val cardColor: Color
    val textColor: Color
    if(text == filter){
        cardColor = Green10
        textColor = White10
    }
    else {
        cardColor = White10
        textColor = Black10
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RectangleShape,
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ){
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.height(40.dp)) {
            Text(
                text = text,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W700,
                fontSize = 15.sp,
                color = textColor,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .fillMaxWidth()
                    .clickable(onClick = {
                        filter = text
                        expandedChange(false)
                    }
                )
            )
        }
    }
}