package com.example.testfinalnow.screen


import android.R
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.db.ProductDatabase
import com.example.testfinalnow.firebase.Orders
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import kotlin.random.Random


//WORKING
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CheckOutScreen(modifier: Modifier, navController: NavController, productDatabase: ProductDatabase, viewModel: ProductViewModel){

    var address by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cart = productDatabase.getProductDao().getAllInCart()
    var productforcart= productDatabase.getProductDao().getAllProduct()

    var j= 0
    var countcart=0

    val context= LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors= topAppBarColors(
                    containerColor= MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor= MaterialTheme.colorScheme.primary,
                ),
                title={
                    Text(text="Your Cart")
                },

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
        Column(modifier= Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            cart.forEach{
                while(j<productforcart.size){
                    if(cart.get(countcart).product.equals(productforcart.get(j).name)){

                        var imageuri= productforcart.get(j).image!!.toUri();

                        AsyncImage(model= imageuri, contentDescription = null, modifier= Modifier
                            .size(100.dp)
                            .padding(12.dp))

                        Text(text=""+productforcart.get(j).returnProduct())

                        Text(text="Price: "+productforcart.get(j).price)

                        Button(
                            onClick = {
                                val product= productDatabase.getProductDao().getProduct(productforcart.get(j).id)
                                product.stock++
                                productDatabase.getProductDao().updateProduct(product)
                                
                                viewModel.removeFromCart(cart.get(j), productDatabase)
                                navController.navigate(route="checkout/"+products)
                            },
                            content = {
                                Text(text="remove from cart")
                            },
                            modifier= Modifier
                                .padding(16.dp)
                        )

                        HorizontalDivider()
                    }
                    j++
                }
                countcart++
                j=0
            }
            countcart=0

            Text(text="Full Price: "+productDatabase.getProductDao().getCartPrice(), modifier = Modifier
                .padding(4.dp))

            Text(text="Fill out your information!", modifier = Modifier
                .padding(4.dp))

            OutlinedTextField(
                value= address,
                onValueChange = {
                    address= it
                },
                label= {
                    Text("Your address here...")
                }
            )

            OutlinedTextField(
                value= user,
                onValueChange = {
                    user= it
                },
                label= {
                    Text("Who is making this order?")
                }
            )
            OutlinedTextField(
                value= email,
                onValueChange = {
                    email= it
                },
                label= {
                    Text("Your email address")
                }
            )
            OutlinedTextField(
                value= phone,
                onValueChange = {
                    phone= it
                },
                label= {
                    Text("Your phone number")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                )

            Button(modifier = Modifier
                .padding(20.dp)
                .size(width= 200.dp, height = 50.dp),
                onClick={

                    var itemslist= ""
                    var pricefinal=0.0

                    j=0

                    var i=j+1

                    cart.forEach {
                        itemslist=""+i+") "+itemslist+cart.get(j).product+"\n"
                        pricefinal+= cart.get(j).price
                        j++
                    }
                    if(address=="" || user=="" || email=="" || phone==""){
                        Toast.makeText(context,"Please fill out all the required information!", Toast.LENGTH_SHORT).show()
                    }else{
                        lateinit var db: FirebaseFirestore

                        var order= Orders(
                            Address = address,
                            Date = ""+ Timestamp.now().toDate(),
                            ItemsList = itemslist,
                            ItemsQuantity = j,
                            OrderId = Random.nextInt(0,100000),
                            Price = pricefinal,
                            User = user,
                            Email=email,
                            Phone=phone
                        )

                        db= FirebaseFirestore.getInstance()

                        db
                            .collection("Orders")
                            .document(order.OrderId.toString())
                            .set(order)

                        j=0
                        navController.navigate("main/")

                        val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                        val notification= NotificationCompat.Builder(context,"channel_id")
                            .setContentText("Your order has been completed!\nOrder ID: "+order.OrderId)
                            .setContentTitle("Order Completed!")
                            .setSmallIcon(R.drawable.ic_menu_info_details)
                            .build()

                        notificationManager.notify(1, notification)

                        var j=0

                        cart.forEach{
                            productDatabase.getProductDao().deleteFromCart(cart.get(j).id)
                            j++
                        }
                    }
                }
            ){
                Text(text="Complete your Order", modifier = Modifier)
            }

            Button(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    navController.navigate(route="editorder/"+products)
                },
                content = {
                    Text("Edit Order")
                },
            )

            Button(
                modifier = Modifier
                    .padding(20.dp),
                onClick = {
                    navController.navigate(route="allorders/"+products)
                },
                content = {
                    Text(text="All Orders", modifier = Modifier)
                },
            )

        }
    }
}



