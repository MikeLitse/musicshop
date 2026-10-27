package com.example.testfinalnow.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testfinalnow.db.ProductDatabase
import com.example.testfinalnow.db.Store

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStore(modifier: Modifier= Modifier, navController: NavController, productDatabase: ProductDatabase){

    var storeproducts by remember{ mutableStateOf("") }
    var storeaddress by remember{ mutableStateOf("") }
    var ermac by remember{ mutableStateOf("") }

    val context= LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors= topAppBarColors(
                    containerColor= MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor= MaterialTheme.colorScheme.primary,
                ),
                title={
                    Text(text="Add Store")
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
                navController.navigate(route="storestatus/"+products)
            }){
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription=null)
            }
        },

        )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text="Add a new store", modifier = Modifier.padding(16.dp))

            Text(text="To add a new store, please fill out the textboxes with the appropriate information!")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= storeaddress,
                onValueChange = {
                    storeaddress= it
                },
                label= {
                    Text("Insert store address")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= storeproducts,
                onValueChange = {
                    storeproducts= it
                },
                label= {
                    Text("Insert store products")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if(storeaddress=="" || storeproducts==""){
                        ermac="Please fill out all the textboxes!"
                    }else{
                        if(storeproducts.toInt() is Int && storeproducts.toInt()>=0){
                            val store= Store(
                                storeId = 0,
                                Products = storeproducts.toInt(),
                                storeAddress = storeaddress
                            )
                            productDatabase.getProductDao().addStore(store)
                            Toast.makeText(context,"Store Added!", Toast.LENGTH_SHORT).show()

                        }else{
                            ermac="Please enter valid sales!"
                        }
                    }
                },
                modifier= Modifier
                    .padding(16.dp)
            ){
                Text(text="Add Store", modifier = Modifier)
            }

            Text(text=ermac, modifier = Modifier, color= MaterialTheme.colorScheme.error)

            Spacer(modifier = Modifier.height(16.dp))



            Button(onClick = {
                navController.navigate(route="main/"+products)
            }){
                Text("Return", modifier= Modifier)
            }
        }

    }


}

