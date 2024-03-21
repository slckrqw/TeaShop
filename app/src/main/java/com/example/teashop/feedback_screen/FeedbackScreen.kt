package com.example.teashop.feedback_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.teashop.R
import com.example.teashop.data.DataSource
import com.example.teashop.data.Product
import com.example.teashop.product_screen.ProductScreen
import com.example.teashop.reusable_interface.SimpleTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.Grey10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily


class FeedbackScreen {
    @Composable
    fun MakeFeedbackScreen(backScreen: (Int)->Unit = {}, product: Product){

        var feedbackSorting by remember{mutableStateOf("Новые")}
        var i = 0
        var i1 = 0
        var newFeedbackScreen by remember{ mutableIntStateOf(-1) }
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        val launcher = rememberLauncherForActivityResult(
            contract =
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }
        var feedbackTextContent by remember{ mutableStateOf("") }

        when(newFeedbackScreen) {

           -2-> Column(
                modifier = Modifier.fillMaxSize()
           ){
               SimpleTopCard(backScreen = {
                   newFeedbackScreen = it
               }).MakeTopCard(
                   drawableId = R.drawable.back_arrow,
                   textId = R.string.NewFeedbackName
               )
               Card(
                   colors = CardDefaults.cardColors(containerColor = White10),
                   shape = RectangleShape,
                   modifier = Modifier
                       .padding(bottom = 10.dp)
                       .height(100.dp)
                       .fillMaxWidth()
                       .shadow(elevation = 3.dp)
               ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 5.dp)
                    ){
                        Image(
                            painter = painterResource(product.imageResourceId),
                            modifier = Modifier.size(40.dp),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(product.nameId),
                            fontFamily = montserratFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 10.dp)
                    ){
                        while(i1<5){
                            Icon(
                                painter = painterResource(R.drawable.star),
                                tint = Yellow10,
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp),
                                contentDescription = null
                            )
                            i1++
                        }
                    }
               }
               Card(
                   colors = CardDefaults.cardColors(containerColor = White10),
                   shape = RoundedCornerShape(10.dp),
                   modifier = Modifier
                       .fillMaxWidth()
               ){
                   Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 5.dp)
                            .fillMaxWidth()
                   ){
                       Text(
                           text = "Фотографии",
                           fontFamily = montserratFamily,
                           fontSize = 13.sp,
                           fontWeight = FontWeight.W400
                       )
                       Text(
                           text = "До 3 штук",
                           fontFamily = montserratFamily,
                           fontSize = 10.sp,
                           fontWeight = FontWeight.W400,
                           color = Grey10
                       )
                   }
                   Row(
                       modifier = Modifier.padding(bottom = 5.dp)
                   ){
                       Button(
                           colors = ButtonDefaults.buttonColors(containerColor = Grey20),
                           shape = RoundedCornerShape(15.dp),
                           onClick = { launcher.launch("image/*") },
                           contentPadding = PaddingValues(0.dp),
                           modifier = Modifier
                               .padding(start = 10.dp)
                               .size(90.dp)
                       ) {

                       }
                       AsyncImage(
                           model = imageUri,
                           contentDescription = null,
                           modifier = Modifier
                               .padding(start = 10.dp)
                               .size(90.dp)
                               .clip(RoundedCornerShape(12.dp)),
                           contentScale = ContentScale.Crop,
                       )
                   }
                    Text(
                        text = "Ваш отзыв",
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(start = 10.dp, bottom = 5.dp)
                    )
                   TextField(
                       value = feedbackTextContent,
                       onValueChange = {feedbackTextContent = it},
                       shape = RoundedCornerShape(15.dp),
                       colors = TextFieldDefaults.colors(
                           focusedIndicatorColor = White10,
                           unfocusedIndicatorColor = White10,
                           focusedContainerColor = Grey20,
                           unfocusedContainerColor = Grey20,
                           disabledContainerColor = Grey20,
                           disabledTextColor = Black10,
                           focusedTextColor = Black10,
                       ),
                       keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                       modifier = Modifier
                           .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                           .fillMaxWidth()
                           .height(40.dp),
                       maxLines = 20
                   )
               }
           }
           -1 -> Column(
                modifier = Modifier.fillMaxSize()
              ){
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    SimpleTopCard(backScreen).MakeTopCard(
                        drawableId = R.drawable.back_arrow,
                        textId = R.string.FeedbackName
                    )
                    Icon(
                        painter = painterResource(R.drawable.plus_icon),
                        modifier = Modifier
                            .clickable(onClick = { newFeedbackScreen = -2 })
                            .padding(bottom = 10.dp, end = 10.dp)
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
                        .width(110.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sorting_icon),
                            tint = Green10,
                            modifier = Modifier.size(20.dp),
                            contentDescription = null
                        )
                        Text(
                            text = feedbackSorting,
                            fontFamily = montserratFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400,
                        )
                    }
                }
                LazyColumn {
                    items(product.feedBackList.size) { feedback ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = White10),
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(start = 10.dp)
                            ) {
                                Text(
                                    text = product.feedBackList[feedback].userId,
                                    fontFamily = montserratFamily,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W400,
                                )
                                Row(
                                    modifier = Modifier.padding(bottom = 5.dp)
                                ) {
                                    while (i < product.feedBackList[feedback].rate) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.star),
                                            tint = Yellow10,
                                            modifier = Modifier.size(15.dp),
                                            contentDescription = null
                                        )
                                        i++
                                    }
                                }
                                Text(
                                    text = product.feedBackList[feedback].content,
                                    fontFamily = montserratFamily,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W400,
                                    modifier = Modifier.padding(bottom = 5.dp)
                                )
                                Image(
                                    painter = painterResource(id = product.feedBackList[feedback].imageResourceId),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null
                                )
                            }
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
        FeedbackScreen().MakeFeedbackScreen(product = DataSource().loadProducts()[0])
    }
}