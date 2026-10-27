package com.example.testfinalnow.screen

import android.R
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.testfinalnow.db.Product
import com.example.testfinalnow.db.ProductDatabase
import coil3.compose.AsyncImage
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.firebase.Orders
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun EditOrder(modifier: Modifier= Modifier, navController: NavController, productDatabase: ProductDatabase, viewModel: ProductViewModel){

    lateinit var db: FirebaseFirestore

    db= FirebaseFirestore.getInstance()

    val collectionReference: CollectionReference= db.collection("Orders")


    var querytextresult by remember { mutableStateOf("") }
    val context= LocalContext.current

    var orderId  by remember { mutableStateOf("") }
    var address by remember{ mutableStateOf("") }
    var date by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var itemsList by remember{ mutableStateOf("") }
    var itemsQuantity by remember{ mutableStateOf("") }
    var phone by remember{ mutableStateOf("") }
    var price by remember{ mutableStateOf("") }
    var user by remember{ mutableStateOf("") }

    var enabled by remember{ mutableStateOf(true) }

    var ermac by remember{ mutableStateOf("") }






        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(text="Edit order", modifier= Modifier.padding(24.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text="If you want to update an existent product, please provide the id!")

            OutlinedTextField(
                value= orderId,
                onValueChange = {
                    orderId= it
                },
                label= {
                    Text("OrderId")
                },
                enabled = enabled
            )

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value= address,
                onValueChange = {
                    address= it
                },
                label= {
                    Text("Address")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= email,
                onValueChange = {
                    email= it
                },
                label= {
                    Text("Email")
                }
            )


            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value= phone,
                onValueChange = {
                    phone= it
                },
                label= {
                    Text("Phone")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))



            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= user,
                onValueChange = {
                    user= it
                },
                label= {
                    Text("User")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text=ermac, modifier = Modifier, color= MaterialTheme.colorScheme.error)

            Button(
                onClick = {

                    if(orderId==""){
                        ermac="Please fill out the id"
                    }else{

                        if(orderId.toInt() is Int){
                            val query= collectionReference.where(
                                Filter.or(
                                    Filter.equalTo("orderId",orderId.toInt())
                                )
                            )

                            enabled=false

                            query
                                .get().addOnSuccessListener {
                                        queryDocumentSnapshots ->
                                    var result=""

                                    for(documentSnapshot in queryDocumentSnapshots){
                                        val orderaddress=documentSnapshot["address"]
                                        val orderuser= documentSnapshot["user"]
                                        val orderDate= documentSnapshot["date"]
                                        val orderemail= documentSnapshot["email"]
                                        val orderitemsQuantity= documentSnapshot["itemsQuantity"]
                                        val orderitemsList= documentSnapshot["itemsList"]
                                        val orderprice= documentSnapshot["price"]
                                        val orderphone= documentSnapshot["phone"]
                                        address=""+orderaddress
                                        user=""+orderuser
                                        date=""+orderDate
                                        email=""+orderemail
                                        itemsQuantity=""+orderitemsQuantity
                                        itemsList=""+orderitemsList
                                        price=""+orderprice
                                        phone=""+orderphone

                                    }
                                    if(address==""){
                                        Toast.makeText(context,"This order does not exist, please enter an other id!", Toast.LENGTH_SHORT).show()
                                    }else{
                                        querytextresult=result
                                    }

                                }.addOnFailureListener {
                                    Log.d("FireStore", "Document does not exist")
                                }

                        }else{
                            ermac= "Please provide a valid id"
                        }

                        Toast.makeText(context,"Here is your order!", Toast.LENGTH_SHORT).show()
                    }

                },
                modifier= Modifier
                    .padding(16.dp)
            ){
                Text(text="Find my order", modifier = Modifier)
            }

            Button(onClick = {

                enabled=true

                var order= Orders(
                    Address = address,
                    Date = ""+ Timestamp.now(),
                    ItemsList = itemsList,
                    ItemsQuantity = itemsQuantity.toInt(),
                    OrderId = orderId.toInt(),
                    Price = price.toDouble(),
                    User = user,
                    Email=email,
                    Phone=phone
                )

                db= FirebaseFirestore.getInstance()

                db
                    .collection("Orders")
                    .document(order.OrderId.toString())
                    .set(order)

                val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val notification= NotificationCompat.Builder(context,"channel_id")
                    .setContentText("Your order has been updated!\nOrder ID: "+order.OrderId)
                    .setContentTitle("Order Updated!")
                    .setSmallIcon(R.drawable.ic_menu_info_details)
                    .build()

                notificationManager.notify(1, notification)



            }){
                Text("Update Order", modifier= Modifier)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.navigate(route="main/"+products)
            }){
                Text("Return", modifier= Modifier)
            }

        }
}



