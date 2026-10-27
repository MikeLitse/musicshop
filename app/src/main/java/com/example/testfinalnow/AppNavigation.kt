package com.example.testfinalnow

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testfinalnow.db.ProductDatabase
import com.example.testfinalnow.db.Store
import com.example.testfinalnow.screen.AddProduct
import com.example.testfinalnow.screen.AddStore
import com.example.testfinalnow.screen.AllOrders
import com.example.testfinalnow.screen.BuyProduct
import com.example.testfinalnow.screen.CasseteScreen
import com.example.testfinalnow.screen.CdScreen
import com.example.testfinalnow.screen.CheckOutScreen
import com.example.testfinalnow.screen.DeleteProduct
import com.example.testfinalnow.screen.EditOrder
import com.example.testfinalnow.screen.EditProduct
import com.example.testfinalnow.screen.MainScreen
import com.example.testfinalnow.screen.Screen
import com.example.testfinalnow.screen.Screen.AddStore
import com.example.testfinalnow.screen.Screen.EditOrder
import com.example.testfinalnow.screen.StoreStatus
import com.example.testfinalnow.screen.VinylScreen
import kotlin.collections.listOf

var products: Int?=0

@Composable
fun AppNavigation(modifier: Modifier= Modifier, productDatabase: ProductDatabase, viewModel: ProductViewModel){

    val navController= rememberNavController()

    NavHost(navController= navController, startDestination=Screen.Main.route){

        composable(
            route= Screen.Main.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            MainScreen(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.Vinyl.route,
            arguments= listOf(navArgument("id"){
                type= NavType.IntType
            })
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            VinylScreen(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.CheckOut.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            CheckOutScreen(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.Cd.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            CdScreen(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.Cassete.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            CasseteScreen(modifier, navController, productDatabase, viewModel)
        }
        composable(
            route= Screen.AddProduct.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            AddProduct(modifier, navController, productDatabase)
        }
        composable(
            route= Screen.BuyProduct.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            BuyProduct(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.DeleteProduct.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            DeleteProduct(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.AllOrders.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            AllOrders(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.EditProduct.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            EditProduct(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.StoreStatus.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            StoreStatus(modifier, navController, productDatabase, viewModel)
        }

        composable(
            route= Screen.AddStore.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            AddStore(modifier, navController, productDatabase)
        }

        composable(
            route= Screen.EditOrder.route
        ){
            products=it.arguments?.getInt("id")
            Log.d("Args",it.arguments?.getInt("id").toString())
            EditOrder(modifier, navController, productDatabase, viewModel)
        }

    }
}