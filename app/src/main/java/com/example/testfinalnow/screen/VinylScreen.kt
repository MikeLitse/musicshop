package com.example.testfinalnow.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.db.ProductDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VinylScreen(modifier: Modifier= Modifier,
                navController: NavController,
                productDatabase: ProductDatabase,
                viewModel: ProductViewModel
){

    val configuration= LocalConfiguration.current
    when(configuration.orientation){
        Configuration.ORIENTATION_PORTRAIT->{
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors= topAppBarColors(
                            containerColor= MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor= MaterialTheme.colorScheme.primary,
                        ),
                        title={
                            Text(text="Record Vinyls")
                        },
                        actions={
                            Icon(
                                imageVector= Icons.Default.ShoppingCart,
                                contentDescription= "Your Cart",
                                modifier= Modifier
                                    .padding(start= 16.dp, end= 16.dp) //Padding left and padding right
                                    .size(28.dp)
                                    .clickable(){
                                        navController.navigate(route="checkout/"+products)
                                    }
                            )
                            Text(text=""+productDatabase.getProductDao().getCountInCart(),
                                modifier = Modifier
                                    .padding(end=8.dp)
                                    .size(28.dp)
                                    .clickable(){
                                        navController.navigate(route="checkout/"+products)
                                    }
                            )
                        }
                    )

                },
                floatingActionButton = {
                    FloatingActionButton(onClick= {
                        navController.navigate(route="main/"+products)
                    }){
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription=null)
                    }
                },

                )
            { innerPadding ->
                //SearchBar()
                Column(modifier= Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){


                    val productList = productDatabase.getProductDao().getAllProduct()
                    var i = 0

                    Spacer(modifier= Modifier
                        .padding(16.dp))

                    productList.forEach {
                        if(productList.get(i).type.equals("Vinyl")){
                            displayProduct(
                                product = productList.get(i),
                                viewModel,
                                navController,
                                productDatabase
                            )
                        }
                        i++
                    }


                    Button(
                        modifier = Modifier
                            .padding(20.dp)
                            .size(150.dp),
                        onClick = {
                            navController.navigate(route="addproduct/"+products)
                        },
                        content = {
                            Icon(imageVector = Icons.Default.Add, contentDescription=null, modifier = Modifier.size(100.dp))
                        },
                    )
                }
            }

        }else ->{
        Scaffold(
            topBar = {
                TopAppBar(
                    colors= topAppBarColors(
                        containerColor= MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor= MaterialTheme.colorScheme.primary,
                    ),
                    title={
                        Text(text="Record Vinyls")
                    },
                    actions={
                        Icon(
                            imageVector= Icons.Default.ShoppingCart,
                            contentDescription= "Your Cart",
                            modifier= Modifier
                                .padding(start= 16.dp, end= 16.dp) //Padding left and padding right
                                .size(28.dp)
                                .clickable(){
                                    navController.navigate(route="checkout/"+products)
                                }
                        )
                        Text(text=""+productDatabase.getProductDao().getCountInCart(),
                            modifier = Modifier
                                .padding(end=8.dp)
                                .size(28.dp)
                                .clickable(){
                                    navController.navigate(route="checkout/"+products)
                                }
                        )
                    }
                )

            },
            floatingActionButton = {
                FloatingActionButton(onClick= {
                    navController.navigate(route="main/"+products)
                }){
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription=null)
                }
            },

            )
        { innerPadding ->
            //SearchBar()
            Row(modifier= Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(innerPadding),
                verticalAlignment = Alignment.CenterVertically
            ){


                    val productList = productDatabase.getProductDao().getAllProduct()
                    var i = 0

                    Spacer(modifier= Modifier
                        .padding(16.dp))

                    productList.forEach {
                        if(productList.get(i).type.equals("Vinyl")){
                            displayProductHorizontal(
                                product = productList.get(i),
                                viewModel,
                                navController,
                                productDatabase
                            )
                        }
                        i++
                    }


                    Button(
                        modifier = Modifier
                            .padding(20.dp)
                            .size(150.dp),
                        onClick = {
                            navController.navigate(route="addproduct/"+products)
                        },
                        content = {
                            Icon(imageVector = Icons.Default.Add, contentDescription=null, modifier = Modifier.size(100.dp))
                        },
                    )


            }
        }
        }
    }


}

