package com.example.testfinalnow.screen

import android.content.Context
import android.net.Uri
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
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.testfinalnow.db.Product
import com.example.testfinalnow.db.ProductDatabase
import coil3.compose.AsyncImage
import com.example.testfinalnow.ProductViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun EditProduct(modifier: Modifier= Modifier, navController: NavController, productDatabase: ProductDatabase, viewModel: ProductViewModel){

    //Product vars

    val product= productDatabase.getProductDao().getProduct(viewModel.productClickedId)

    var id by remember{ mutableStateOf(""+product.id) }
    var name by remember{ mutableStateOf(""+product.name) }
    var type by remember{ mutableStateOf(""+product.type) }
    var artist by remember{ mutableStateOf(""+product.artist) }
    var stock by remember{ mutableStateOf(""+product.stock) }
    var price by remember{ mutableStateOf(""+product.price) }
    var ermac by remember{ mutableStateOf("") }

    val context= LocalContext.current

    //Image Selector vars
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    imageUri=product.image!!.toUri();
    Card(){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text("Add a new product")

            Spacer(modifier = Modifier.height(16.dp))

            Text("If you want to update an existent product, please provide the id!")

            OutlinedTextField(
                value= id,
                onValueChange = {
                    id= it
                },
                label= {
                    Text("Insert id")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= name,
                onValueChange = {
                    name= it
                },
                label= {
                    Text("Insert name")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= type,
                onValueChange = {
                    type= it
                },
                label= {
                    Text("Insert type")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= artist,
                onValueChange = {
                    artist= it
                },
                label= {
                    Text("Insert artist")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= stock,
                onValueChange = {
                    stock= it
                },
                label= {
                    Text("Insert stock")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value= price,
                onValueChange = {
                    price= it
                },
                label= {
                    Text("Insert price")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))


            AsyncImage(model= product.image!!.toUri(), contentDescription = null, modifier= Modifier.size(200.dp))

            Button(
                onClick = {
                    if(id=="" || name=="" || type=="" || artist=="" || stock=="" || price =="" || imageUri==null){
                        ermac="Please fill out all the textboxes!"
                    }else{
                        if(id.toInt() is Int && id.toInt()>0){
                            val imagePath = imageUri?.let { uri ->
                                saveImageToInternalStorage(context, uri, name)
                            }
                            if(stock.toInt() is Int && stock.toInt()>0){
                                if(price.toDouble() is Double && price.toDouble()>0){

                                    val product= Product(id.toInt(),name,type,artist,stock.toInt(),product.image,price.toDouble())
                                    productDatabase.getProductDao().updateProduct(product)
                                    Toast.makeText(context,"Product Added!", Toast.LENGTH_SHORT).show()
                                }else{
                                    ermac="please enter a valid price"
                                }
                            }else{
                                ermac="Please enter a valid stock!"
                            }
                        }else{
                            ermac="Please enter a valid id!"
                        }

                        if(type=="Vinyl"){
                            navController.navigate(route="vinyl/"+products)
                        }else if(type=="CD"){
                            navController.navigate(route="cd/"+products)
                        }else if(type=="Cassette"){
                            navController.navigate(route="cassete/"+products)
                        }
                    }

                },
                modifier= Modifier
                    .padding(16.dp)
            ){
                Text(text="Update Product", modifier = Modifier)
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

/*
fun returnIfFound(productDatabase: ProductDatabase, id: Int): Boolean{
    var flag= false
    val productsList= productDatabase.getProductDao().getAllProduct()
    var i=0
    productsList.forEach {
        if(id==productsList.get(i).id) {
            flag= true
        }
    }
    return flag
}

fun saveImageToInternalStorage(
    context: Context,
    uri: Uri,
    productName: String
): String? {
    return try {
        val directory = File(context.filesDir, "product_images")
        if (!directory.exists()) directory.mkdirs()

        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val fileName = "product${productName}_$timeStamp.jpg"
        val file = File(directory, fileName)

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

 */