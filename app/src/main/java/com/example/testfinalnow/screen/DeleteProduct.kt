package com.example.testfinalnow.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.db.ProductDatabase


//TESTING: READ DATA FROM DB AND ADD IT TO UI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteProduct(modifier: Modifier= Modifier,
                navController: NavController,
                productDatabase: ProductDatabase,
                viewModel: ProductViewModel,
){
    val context= LocalContext.current
    val productDelete = productDatabase.getProductDao().getProduct(viewModel.productClickedForDelete)
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        AsyncImage(model= productDelete.image, contentDescription = null, modifier= Modifier
            .size(350.dp)
            .padding(top=128.dp, bottom=16.dp))

        Text(text= productDelete.returnProduct(), modifier = Modifier.padding(16.dp))

        Button(modifier = Modifier
            .padding(20.dp)
            .size(width= 200.dp, height = 50.dp),
            onClick={
                viewModel.deleteProduct(productDelete, productDatabase)
                Toast.makeText(context,"Product Deleted!", Toast.LENGTH_SHORT).show()
                navController.navigate(route="main/"+products)
            }

        ){
            Text(text="Delete Product", modifier = Modifier)
        }

        Button(onClick = {
            navController.navigate(route="main/"+products)
        },
            modifier= Modifier.padding(16.dp)
        ){
            Text("Return", modifier= Modifier)
        }

    }
}
