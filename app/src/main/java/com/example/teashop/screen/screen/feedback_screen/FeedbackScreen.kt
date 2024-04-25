package com.example.teashop.screen.screen.feedback_screen

import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.teashop.R
import com.example.teashop.data.enums.SortingType
import com.example.teashop.data.model.pagination.review.ReviewFilter
import com.example.teashop.data.model.pagination.review.ReviewPagingRequest
import com.example.teashop.data.model.pagination.review.ReviewSorter
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.review.Review
import com.example.teashop.data.storage.TokenStorage
import com.example.teashop.navigation.Navigation
import com.example.teashop.navigation.Screen
import com.example.teashop.reusable_interface.MakeEmptyListScreen
import com.example.teashop.reusable_interface.cards.MakeFeedbackCard
import com.example.teashop.reusable_interface.cards.MakeTopCard
import com.example.teashop.ui.theme.Black10
import com.example.teashop.ui.theme.Green10
import com.example.teashop.ui.theme.White10
import com.example.teashop.ui.theme.Yellow10
import com.example.teashop.ui.theme.montserratFamily

var sorting = SortingType.NEW //TODO add review sort

@Composable
fun LaunchFeedbackScreen(
    navController: NavController,
    product: ProductFull?,
    viewModel: FeedbackViewModel = viewModel(),
    reviewSorter: ReviewSorter = ReviewSorter(),
    reviewFilter: ReviewFilter = ReviewFilter(),
){
    val reviewView by viewModel.review.observeAsState()
    val context = LocalContext.current
    val tokenStorage = remember {
        TokenStorage()
    }
    product?.id?.let {
        reviewFilter.productId = it
    }
    var existsReview by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val token = tokenStorage.getToken(context)
        viewModel.getAllReviewByProduct(
            token,
            ReviewPagingRequest(
                filter = reviewFilter,
                sorter = reviewSorter
            ),
            onError = {
                Toast.makeText(context, "Получение списка отзывов временно недоступно", Toast.LENGTH_SHORT).show()
            }
        )

        if (token.isNullOrEmpty()) {
            existsReview = true
        } else {
            reviewFilter.productId?.let {
                viewModel.existsReview(
                    token,
                    it,
                    onSuccess = {
                        existsReview = true
                    }
                )
            }
        }
    }

    reviewView?.let { reviews ->
        Navigation(navController = navController) {
            MakeFeedbackScreen(
                navController,
                reviews,
                product,
                existsReview,
                tokenStorage,
                context,
            )
        }
    }
}

@Composable
fun MakeFeedbackScreen(
    navController: NavController,
    reviewList: List<Review?>,
    product: ProductFull?,
    existsReview: Boolean,
    tokenStorage: TokenStorage,
    context: Context
){
    var sortingText by remember {
        mutableStateOf("Новые")
    }
    sortingText = sorting.value
    var expandedChange by remember{mutableStateOf(false)}
    var reviewListSum = 0.0
    var averageRate = 0.0
    reviewList.let {
        if (reviewList.isNotEmpty()) {
            reviewList.forEach {
                reviewListSum += it?.stars ?: 0
            }
            averageRate = reviewListSum / reviewList.size
        }
    }
    
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
            if (!existsReview) {
                Icon(
                    painter = painterResource(R.drawable.plus_icon),
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "product",
                                    product
                                )
                                navController.navigate(Screen.NewFeedback.route)
                            }
                        )
                        .size(20.dp),
                    tint = White10,
                    contentDescription = null
                )
            }
        }
        if(reviewList.isEmpty()){
            MakeEmptyListScreen(type = "Отзывов")
        }else {
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
                            text = averageRate.toString(),
                            fontFamily = montserratFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        )
                        Text(
                            text = "${reviewList.size} отзывов",
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
                    .clip(RoundedCornerShape(10.dp))
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
                        text = sortingText,
                        fontFamily = montserratFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W400,
                    )
                }
            }
            LazyColumn {
                reviewList.let {
                    items(it.size, {  index -> it[index]?.id ?: index }) { review ->
                        reviewList[review]?.let { item -> MakeFeedbackCard(item) }
                    }
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
            SheetTextCatalog(SortingType.NEW, expandedChange)
            SheetTextCatalog(SortingType.OLD, expandedChange)
            SheetTextCatalog(SortingType.NEGATIVE, expandedChange)
            SheetTextCatalog(SortingType.POSITIVE, expandedChange)
        }
    }
}

@Composable
fun SheetTextCatalog(text: SortingType, expandedChange: (Boolean)->Unit){
    val cardColor: Color
    val textColor: Color
    if(text == sorting){
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
                text = text.value,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.W700,
                fontSize = 15.sp,
                color = textColor,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            sorting = text
                            expandedChange(false)
                        }
                    )
            )
        }
    }
}