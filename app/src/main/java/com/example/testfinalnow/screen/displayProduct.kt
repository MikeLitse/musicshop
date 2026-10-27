package com.example.testfinalnow.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.db.Product
import com.example.testfinalnow.db.ProductDatabase
import androidx.core.net.toUri


@Composable
fun displayProduct(
    product: Product,
    viewModel: ProductViewModel,
    navController: NavController,
    productDatabase: ProductDatabase
){
    var imageuri= product.image!!.toUri();
    var text: String= "(Stock:"+product.stock+")"

    AsyncImage(model= imageuri, contentDescription = null, modifier= Modifier
        .size(200.dp)
        .clickable{
            if(product.stock>0){
                text= "(Stock: " + product.stock +")"
                viewModel.productClickedId=product.id
                navController.navigate(route ="buyproduct/"+products)

            }else{
                text="Out of Stock"
            }
            viewModel.productClickedImage=product.image.toString()
        }
    )

    Text(text= product.returnProduct(), modifier = Modifier
        .padding(10.dp)
        .clickable{
            if(product.stock>0){
                text= "(Stock: " + product.stock +")"
                viewModel.productClickedId=product.id
                navController.navigate(route ="buyproduct/"+products)

            }else{
                text="Out of Stock"
            }
            viewModel.productClickedImage=product.image.toString()

        }
    )

    Text(text="Price: "+product.price, modifier = Modifier.padding(16.dp))

    Text(text=text, modifier = Modifier
        .padding(16.dp)
    )

    Button(
        onClick = {
            viewModel.productClickedId=product.id
            navController.navigate("editproduct/"+products)
        },
        content = {
            Icon(
                imageVector= Icons.Default.Settings,
                contentDescription= "Edit Product",
                modifier= Modifier
                    .size(28.dp)
                    .clickable(){
                        viewModel.productClickedId=product.id
                        navController.navigate("editproduct/"+products)
                    }
            )
        },
        modifier= Modifier
            .padding(16.dp)
    )

    Button(
        onClick = {
            viewModel.productClickedForDelete=product.id
            navController.navigate("deleteproduct/"+products)
        },
        content = {
            Icon(
                imageVector= Icons.Default.Delete,
                contentDescription= "Edit Product",
                modifier= Modifier
                    .size(28.dp)
                    .clickable(){
                        viewModel.productClickedForDelete=product.id
                        navController.navigate("deleteproduct/"+products)
                    }
            )
        },
        modifier= Modifier
        .padding(16.dp)
    )
    HorizontalDivider(modifier= Modifier.padding(16.dp))
}


