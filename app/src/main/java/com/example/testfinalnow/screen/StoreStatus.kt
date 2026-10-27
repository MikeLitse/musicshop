
package com.example.testfinalnow.screen

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.testfinalnow.ProductViewModel
import com.example.testfinalnow.db.ProductDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreStatus(modifier: Modifier= Modifier, navController: NavController, productDatabase: ProductDatabase, viewModel: ProductViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Store Status")
                },
                actions = {

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(route = "allorders/" + products)
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },

        )
    { innerPadding ->


        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = " Store Status ", modifier = Modifier.padding(8.dp)
                    .size(20.dp)
            )

            val storeList = productDatabase.getProductDao().getAllStores()
            var i = 0
            storeList.forEach {
                Text(
                    text = "Store Id: " + storeList.get(i).storeId,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Store Address: " + storeList.get(i).storeAddress,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Count of products in store: " + storeList.get(i).Products,
                    modifier = Modifier.padding(4.dp)
                )

                HorizontalDivider(modifier = Modifier.padding(4.dp))
                i++
            }

            i = 0

            Button(
                onClick = {
                    navController.navigate("addstore/" + products)
                },
                content = {
                    Text(text = "Add Store", modifier = Modifier.padding(16.dp))
                },
                modifier = Modifier
                    .padding(16.dp)
            )

        }
    }
}

