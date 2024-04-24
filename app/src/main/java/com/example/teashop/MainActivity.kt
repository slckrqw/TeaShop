package com.example.teashop

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teashop.data.enums.CatalogConfig
import com.example.teashop.data.model.category.Category
import com.example.teashop.data.model.order.Order
import com.example.teashop.screen.screen.basket_screen.LaunchBasketScreen
import com.example.teashop.screen.screen.basket_screen.LaunchOrderScreen
import com.example.teashop.screen.screen.category_screen.LaunchCategoryScreen
import com.example.teashop.data.model.product.ProductFull
import com.example.teashop.data.model.category.ParentCategory
import com.example.teashop.data.model.user.User
import com.example.teashop.screen.screen.feedback_screen.LaunchFeedbackScreen
import com.example.teashop.screen.screen.feedback_screen.LaunchNewFeedbackScreen
import com.example.teashop.screen.screen.main_screen.LaunchMainScreen
import com.example.teashop.screen.screen.product_screen.LaunchProductScreen
import com.example.teashop.screen.screen.profile_screen.sign_in.LaunchLogScreen
import com.example.teashop.screen.screen.profile_screen.profile.LaunchProfileScreen
import com.example.teashop.screen.screen.profile_screen.sign_in.LaunchRegScreen
import com.example.teashop.screen.screen.catalog_screen.LaunchCatalogScreen
import com.example.teashop.navigation.Screen
import com.example.teashop.screen.screen.basket_screen.LaunchAddressChangeScreen
import com.example.teashop.screen.screen.profile_screen.LaunchOrderDescriptionScreen
import com.example.teashop.screen.screen.profile_screen.LaunchOrdersScreen
import com.example.teashop.screen.screen.profile_screen.user_data.LaunchUserDataScreen
import com.example.teashop.screen.screen.profile_screen.LaunchUserFeedbackScreen
import com.example.teashop.screen.screen.search_screen.LaunchSearchScreen
import com.example.teashop.ui.theme.Grey20
import com.example.teashop.ui.theme.TeaShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = Color.parseColor("#1FAF54")
            window.navigationBarColor=getColor(R.color.white)
            TeaShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Grey20
                ) {
                    TeaShopApp()
                }
            }
        }
    }
}

@Composable
fun TeaShopApp(){
    val navController = rememberNavController()
    
    NavHost(navController = navController,
        startDestination = Screen.Main.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition  = { fadeIn() },
        popExitTransition  = { fadeOut() }){

        composable(Screen.Main.route){
            LaunchMainScreen(navController = navController)
        }

        composable(Screen.Search.route){
            LaunchSearchScreen(navController = navController)
        }

        composable(Screen.Basket.route){
            LaunchBasketScreen(navController = navController)
        }

        composable(Screen.Profile.route){
            LaunchProfileScreen(navController = navController)
        }

        composable(Screen.Catalog.route){
            val config: CatalogConfig? = navController.previousBackStackEntry?.savedStateHandle?.get("config")
            val category: Category? = navController.previousBackStackEntry?.savedStateHandle?.get("category")
            val searchString: String? = navController.previousBackStackEntry?.savedStateHandle?.get("searchString")
            LaunchCatalogScreen(navController, config, category, searchString)
        }

        composable(Screen.Product.route){
            val productId: Long? =  navController.previousBackStackEntry?.savedStateHandle?.get("productId")
            val isFavorite: Boolean? = navController.previousBackStackEntry?.savedStateHandle?.get("isFavorite")
            LaunchProductScreen(navController, productId, isFavorite)
        }

        composable(Screen.Category.route) {
            val type: ParentCategory? = navController.previousBackStackEntry?.savedStateHandle?.get("type")
            LaunchCategoryScreen(
                navController = navController,
                type = type,
            )
        }

        composable(Screen.Feedback.route){
            val productId: Long? = navController.previousBackStackEntry?.savedStateHandle?.get("productId")
            LaunchFeedbackScreen(navController = navController, productId = productId)
        }

        composable(Screen.NewFeedback.route){
            val product: ProductFull? = navController.previousBackStackEntry?.savedStateHandle?.get("product")
            LaunchNewFeedbackScreen(navController = navController, product = product)
        }

        composable(Screen.Order.route){
            LaunchOrderScreen(navController = navController)
        }

        composable(Screen.Reg.route){
            LaunchRegScreen(navController = navController)
        }

        composable(Screen.Log.route){
            LaunchLogScreen(navController = navController)
        }
        
        composable(Screen.Orders.route){
            LaunchOrdersScreen(navController = navController)
        }

        composable(Screen.OrderDescription.route){
            val order: Order? = navController.previousBackStackEntry?.savedStateHandle?.get("order")
            LaunchOrderDescriptionScreen(navController = navController, order = order)
        }
        
        composable(Screen.UserData.route){
            val user: User? = navController.previousBackStackEntry?.savedStateHandle?.get("user")
            LaunchUserDataScreen(navController = navController, user = user)
        }
        
        composable(Screen.UserFeedback.route){
            LaunchUserFeedbackScreen(navController = navController)
        }
        
        composable(Screen.AddressChange.route){
            LaunchAddressChangeScreen(navController = navController)
        }
    }
}
