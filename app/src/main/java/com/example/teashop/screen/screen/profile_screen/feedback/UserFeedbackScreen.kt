package com.example.teashop.screen.screen.profile_screen.feedback

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.rememberAsyncImagePainter
import com.example.teashop.R
import com.example.teashop.data.model.pagination.review.ReviewFilter
import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.pagination.review.ReviewSorter
import com.example.teashop.data.model.review.Review
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.common.Navigation
import com.example.teashop.navigation.common.Screen
import com.example.teashop.reusable_interface.MakeEmptyListScreen
import com.example.teashop.reusable_interface.cards.MakeFeedbackCard
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.screen.screen.profile_screen.profile.ProfileViewModel
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.montserratFamily

@Composable
fun LaunchUserFeedbackScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(),
    reviewSorter: ReviewSorter = ReviewSorter(),
    reviewFilter: ReviewFilter = ReviewFilter(),
){
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }
    val reviewView by viewModel.review.observeAsState()

    reviewFilter.byCurrentUser = true

    LaunchedEffect(Unit) {
        viewModel.getAllReviewByCurrent(
            tokenStorage.getToken(context)!!,
            ReviewPagingRequest(
                filter = reviewFilter,
                sorter = reviewSorter
            ),
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

    reviewView?.let { reviews ->
        Navigation(navController = navController) {
            MakeUserFeedbackScreen(
                navController,
                reviews,
                viewModel,
                tokenStorage,
                context
            )
        }
    }
}
@Composable
fun MakeUserFeedbackScreen(
    navController: NavController,
    feedbackList: List<Review?>,
    viewModel: ProfileViewModel,
    tokenStorage: TokenStorage,
    context: Context
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MakeTopCard(
            drawableId = R.drawable.back_arrow,
            text = "Мои отзывы",
            navController = navController
        )
        if(feedbackList.isEmpty()){
            MakeEmptyListScreen(type = "Отзывов")
        } else {
            LazyColumn {
                items(feedbackList.size, {feedbackList[it]?.id ?: it}) { feedback ->
                    Column{
                        Card(
                            modifier = Modifier
                                .padding(bottom = 2.dp)
                                .fillMaxWidth()
                                .height(35.dp),
                            colors = CardDefaults.cardColors(containerColor = White10)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 5.dp, horizontal = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    feedbackList[feedback]?.let {
                                        Image(
                                            painter = rememberAsyncImagePainter(it.productImage?.imageUrl),
                                            contentDescription = null,
                                            modifier = Modifier.padding(end = 10.dp)
                                        )
                                        Text(
                                            text = it.productTitle ?: "",
                                            fontFamily = montserratFamily,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.W400,
                                            color = Black10
                                        )
                                    }
                                }
                                Box {
                                    var expanded by remember {
                                        mutableStateOf(false)
                                    }
                                    Icon(
                                        painter = painterResource(R.drawable.dropdown_icon),
                                        contentDescription = null,
                                        tint = Black10,
                                        modifier = Modifier.clickable(
                                            onClick = {
                                                expanded = true
                                            }
                                        )
                                    )
                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier
                                            .background(Grey20)
                                    ) {
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = "Удалить",
                                                    fontFamily = montserratFamily,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.W200,
                                                    color = Black10,
                                                    modifier = Modifier.padding(start = 5.dp)
                                                )
                                            },
                                            onClick = {
                                                expanded = false
                                                feedbackList[feedback]?.let {
                                                    viewModel.deleteReview(
                                                        tokenStorage.getToken(context) ?: "",
                                                        it.id!!,
                                                        onSuccess = {
                                                            Toast.makeText(context, "Вы успешно удалили отзыв", Toast.LENGTH_SHORT).show()
                                                            navController.navigate(
                                                                Screen.UserFeedback.route,
                                                                navOptions = navOptions {
                                                                    popUpTo(navController.graph.id) {
                                                                        inclusive = true
                                                                    }
                                                                }
                                                            )
                                                        },
                                                        onError = {
                                                            Toast.makeText(context, "Не удалось удалить отзыв", Toast.LENGTH_SHORT).show()
                                                        }
                                                    )
                                                }
                                            },
                                            contentPadding = PaddingValues(2.dp),
                                            modifier = Modifier
                                                .height(25.dp)
                                                .width(70.dp)
                                                .background(Grey20)
                                        )
                                    }
                                }
                            }
                        }
                        feedbackList[feedback]?.let { MakeFeedbackCard(review = it) }
                    }
                }
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