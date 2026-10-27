package com.example.testfinalnow.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.R
import com.example.testfinalnow.db.ProductDatabase
import kotlinx.coroutines.launch

var products: Int= 0
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MainScreen(modifier: Modifier= Modifier, navController: NavController, productDatabase: ProductDatabase, viewModel: ProductViewModel){

    val drawerState= rememberDrawerState(
        initialValue = DrawerValue.Closed,
    )
    val scope= rememberCoroutineScope()

    ModalNavigationDrawer(                //Function to navigate with drawer
        drawerState=drawerState,
        drawerContent={
            ModalDrawerSheet {
                DrawerContent(modifier,navController)
            }
        }
    ){                                     //OTIDHPOTE THELW NA FAINETAI ENW EINAI KLEISTO TO DRAWER PREPEI NA BRISKETAI MESA STO
        //MODALDRAWERSHEET
        Scaffold(
            topBar = {
                TopBar(                        //Fun to create TopBar called
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.apply{
                                if(isClosed){
                                    open()
                                }else{
                                    close()
                                }
                            }
                        }

                    }
                    ,
                    navController,
                    productDatabase
                )
            },
        ){ innerPadding ->
            Column(modifier= Modifier
                .padding(innerPadding)
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(text= "Hello, this is my music e-shop app!", modifier= Modifier
                    .padding(8.dp)
                )

                Image(painter = painterResource(R.drawable.testnote), contentDescription = null, modifier = Modifier.size(100.dp))
            }
//android.R.drawable.ic_media_next
        }
    }


}


@Composable
fun DrawerContent(modifier: Modifier= Modifier, navController: NavController){

    Text(text= "Menu", fontSize=24.sp, modifier = Modifier
        .padding(16.dp)
    )

    HorizontalDivider()

    NavigationDrawerItem(
        label= {
            Text(text= "Record Vinyls", fontSize= 20.sp, modifier= Modifier.padding(16.dp))
        },
        selected= false,
        onClick= {
            navController.navigate(route= "vinyl/"+products)
        }
    )

    HorizontalDivider()

    NavigationDrawerItem(
        label= {
            Text(text= "CDs", fontSize= 20.sp, modifier= Modifier.padding(16.dp))
        },
        selected= false,
        onClick= {
            navController.navigate(route= "cd/"+products)
        }
    )

    HorizontalDivider()

    NavigationDrawerItem(
        label= {
            Text(text= "Cassettes", fontSize= 20.sp, modifier= Modifier.padding(16.dp))
        },
        selected= false,
        onClick= {
            navController.navigate(route= "cassete/"+products)
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenDrawer: () ->Unit,
    navController: NavController,
    productDatabase: ProductDatabase

){
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start= 16.dp, end= 16.dp) //Padding left and padding right
                    .size(28.dp)
                    .clickable(){
                        onOpenDrawer()
                    }

            )
        },
        colors= topAppBarColors(
            containerColor= MaterialTheme.colorScheme.primaryContainer,
            titleContentColor= MaterialTheme.colorScheme.primary,
        ),
        title={
            Text(text="E-shop")
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

                    }
            )
        }
    )
}