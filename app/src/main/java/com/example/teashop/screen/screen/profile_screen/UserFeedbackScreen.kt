package com.example.teashop.screen.screen.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.DataSource
import com.example.teashop.navigation.Navigation
import com.example.teashop.reusable_interface.cards.MakeFeedbackCard
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchUserFeedbackScreen(navController: NavController){
    Navigation(navController = navController) {
        MakeUserFeedbackScreen(navController = navController)
    }
}
@Composable
fun MakeUserFeedbackScreen(navController: NavController){
    val feedbackList = DataSource().loadFeedback()
    val product = DataSource().loadShortProducts()[0]
    LazyColumn {
        item{
            MakeTopCard(
                drawableId = R.drawable.back_arrow,
                text = "Мои отзывы",
                navController = navController
            )
        }
        items(feedbackList.size){feedback ->
            Column(

            ){
                Card(
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                        .fillMaxWidth()
                        .height(35.dp),
                    colors = CardDefaults.cardColors(containerColor = White10)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 5.dp, horizontal = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            product?.let {
                                Image(
                                    painter = rememberAsyncImagePainter(model = product.images[0]),
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                                Text(
                                    text = product.title,
                                    fontFamily = montserratFamily,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400,
                                    color = Black10
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(R.drawable.dropdown_icon),
                            contentDescription = null,
                            tint = Black10
                        )
                    }
                }
                MakeFeedbackCard(review = feedbackList[feedback])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewUserFeedback() {
    TeaShopTheme {
    }
}