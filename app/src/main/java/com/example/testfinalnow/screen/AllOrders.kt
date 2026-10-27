package com.example.testfinalnow.screen

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.db.ProductDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllOrders(modifier: Modifier= Modifier,
              navController: NavController,
              productDatabase: ProductDatabase,
              viewModel: ProductViewModel
){

   // val poppinsFamily= FontFamily(
     //   Font(R.font.poppins_bold, FontWeight.Bold)
   // )

    lateinit var db: FirebaseFirestore

    db= FirebaseFirestore.getInstance()

    val collectionReference: CollectionReference= db.collection("Orders")

    Scaffold(
        topBar = {
            TopAppBar(
                colors= topAppBarColors(
                    containerColor= MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor= MaterialTheme.colorScheme.primary,
                ),
                title={
                    Text(text="All Orders")
                },
                actions={

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick= {
                navController.navigate(route="checkout/"+products)
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

            val context= LocalContext.current

            val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            var orderId  by remember { mutableStateOf("") }
            var querytextresult by remember { mutableStateOf("") }


            var querymaxresult by remember { mutableStateOf("") }
            var countresult by remember { mutableStateOf("") }

            var userresult by remember { mutableStateOf("") }

            val querymax= collectionReference.where(
                Filter.or(
                    Filter.greaterThan("orderId",0)
                )
            )

            val querycount= collectionReference.where(
                Filter.or(
                    Filter.greaterThan("orderId",0)
                )
            )

            querycount
                .get().addOnSuccessListener {
                        queryDocumentSnapshots ->
                    var count=0

                    for(documentSnapshot in queryDocumentSnapshots){
                        count++
                    }

                    countresult= ""+count




                }.addOnFailureListener {
                    Log.d("FireStore", "Document does not exist")
                }


            Text(text="Pending orders: "+countresult, modifier= Modifier.padding(16.dp),fontWeight = FontWeight.Bold)
            //Text(text="Pending orders: "+countresult, modifier= Modifier.padding(16.dp), fontFamily = poppinsFamily, fontWeight = FontWeight.Bold)

            HorizontalDivider()

            Text(text="Find out about your Order!", modifier = Modifier
                .padding(16.dp)
            )

            OutlinedTextField(
                value= orderId,
                onValueChange = {
                    orderId= it
                },
                label= {
                    Text("Your orderId here")
                }
            )

            Button(modifier = Modifier
                .padding(20.dp)
                .size(width = 200.dp, height = 50.dp),
                onClick={
                    if(orderId!=""){
                        if(orderId.toInt()<=0){
                            Toast.makeText(context,"Please provide a valid id!", Toast.LENGTH_SHORT).show()
                        }else{

                            val query= collectionReference.where(
                                Filter.or(
                                    Filter.equalTo("orderId",orderId.toInt())
                                )
                            )

                            query
                                .get().addOnSuccessListener {
                                        queryDocumentSnapshots ->
                                    var result=""

                                    for(documentSnapshot in queryDocumentSnapshots){
                                        val address= documentSnapshot["address"]
                                        val user= documentSnapshot["user"]
                                        val orderId= documentSnapshot["orderId"]
                                        val price= documentSnapshot["price"]
                                        result="OrderId: "+orderId+"\nAddress: "+address+"\nBy: "+user+"\nPrice: "+price+"\n"
                                    }
                                    if(result==""){
                                        Toast.makeText(context,"This order does not exist, please enter an other id!", Toast.LENGTH_SHORT).show()
                                    }else{
                                        querytextresult=result

                                        val notification= NotificationCompat.Builder(context,"channel_id")
                                            .setContentText(""+result)
                                            .setContentTitle("Order Completed!")
                                            .setSmallIcon(android.R.drawable.ic_menu_info_details)
                                            .build()

                                        notificationManager.notify(1, notification)
                                    }

                                }.addOnFailureListener {
                                    Log.d("FireStore", "Document does not exi40t")
                                }
                        }
                    }else{
                        Toast.makeText(context,"Please provide a valid id!", Toast.LENGTH_SHORT).show()

                    }



                }
            ){
                Text(text="Track my order", modifier = Modifier)
            }

            Button(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {

                    if(orderId!=""){
                        if(orderId.toInt()<=0 || orderId==""){
                            Toast.makeText(context,"Please provide an order!", Toast.LENGTH_SHORT).show()
                        }else{
                            lateinit var db: FirebaseFirestore
                            db= FirebaseFirestore.getInstance()
                            db.collection("Orders").document(orderId).delete()
                            Toast.makeText(context,"Order Deleted!", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(context,"Please provide an order!", Toast.LENGTH_SHORT).show()
                    }

                },
                content = {
                    Text("Delete Order")
                },
            )

            HorizontalDivider(modifier= Modifier.padding(16.dp))

            Button(
                onClick = {
                    querymax
                        .get().addOnSuccessListener {
                            queryDocumentSnapshots ->
                            var result=""
                            var pricemax=0.0
                            var pricecompare= 0.0


                            var count=0

                                for(documentSnapshot in queryDocumentSnapshots){
                                    val address= documentSnapshot["address"]
                                    val user= documentSnapshot["user"]
                                    val orderId= documentSnapshot["orderId"]
                                    val price= documentSnapshot["price"]

                                    count++

                                    pricecompare= price.toString().toDouble()

                                    if(pricemax<=pricecompare){
                                        pricemax=pricecompare
                                        result=""+user
                                    }

                                }
                                if(result==""){
                                    Toast.makeText(context,"This order does not exist, please enter an other id!", Toast.LENGTH_SHORT).show()
                                }else{
                                    querymaxresult=result
                                    countresult= ""+count

                                    val notification= NotificationCompat.Builder(context,"channel_id")
                                        .setContentText("The user "+ result +" has made" + count +" orders\n"+
                                                "The most expensive order was " +pricemax+ "$")
                                        .setContentTitle("Most Orders by User")
                                        .setSmallIcon(android.R.drawable.ic_menu_info_details)
                                        .build()

                                    notificationManager.notify(1, notification)

                                }

                            }.addOnFailureListener {
                                Log.d("FireStore", "Document does not exist")
                            }

                    },
                    modifier = Modifier.padding(2.dp),
                    content = {
                        Text(text="Max Order")
                    }
                )





            HorizontalDivider(modifier= Modifier.padding(16.dp))

            Text(text="Check how many orders does a user have")

            var userId by remember{ mutableStateOf("")}
            var usercount by remember{ mutableStateOf(0)}
            var usershow by remember{ mutableStateOf("")}

            OutlinedTextField(
                value= userId,
                onValueChange = {
                    userId= it
                },
                label= {
                    Text("Your UserID here")
                }
            )

            Button(modifier = Modifier
                .padding(20.dp)
                .size(width = 200.dp, height = 50.dp),
                onClick={
                    if(userId<=""){
                        Toast.makeText(context,"Please provide a valid id!", Toast.LENGTH_SHORT).show()
                    }else{
                        val query= collectionReference.where(
                            Filter.or(
                                Filter.equalTo("user",userId)
                            )
                        )
                        query
                            .get().addOnSuccessListener {
                                    queryDocumentSnapshots ->
                                var count=0

                                for(documentSnapshot in queryDocumentSnapshots){
                                    val user= documentSnapshot["user"]

                                    if(userId==user){
                                        count++
                                    }
                                }
                                if(count==0){
                                    Toast.makeText(context,"This user doesnt have any orders", Toast.LENGTH_SHORT).show()
                                }else{
                                    usercount=count

                                    if(count==1){
                                        val notification= NotificationCompat.Builder(context,"channel_id")
                                            .setContentText("The user: "+userId+" has made one order")
                                            .setContentTitle("Count of Orders")
                                            .setSmallIcon(android.R.drawable.ic_menu_info_details)
                                            .build()

                                        notificationManager.notify(1, notification)
                                    }else{
                                        val notification= NotificationCompat.Builder(context,"channel_id")
                                            .setContentText("The user: "+userId+" has made "+usercount+" orders")
                                            .setContentTitle("Count of Orders")
                                            .setSmallIcon(android.R.drawable.ic_menu_info_details)
                                            .build()

                                        notificationManager.notify(1, notification)
                                    }

                                }

                            }.addOnFailureListener {
                                Log.d("FireStore", "Document does not exist")
                            }
                    }
                }
            ){
                Text(text="Check a user", modifier = Modifier)
            }


            HorizontalDivider(modifier= Modifier.padding(16.dp))

            Button(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    navController.navigate(route="storestatus/"+products)
                },
                content = {
                    Text("Store Status")
                },
            )


        }
    }
}


