package com.example.testfinalnow

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.testfinalnow.ui.theme.TestFinalNowTheme
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.testfinalnow.db.Product
import com.example.testfinalnow.db.ProductDatabase
import com.example.testfinalnow.db.Store
import com.example.testfinalnow.db.StoreItems

//Bug, check id before insertion in db

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_foreground, null)


        val myProductDatabase= ProductDatabase.getInstance(context = applicationContext)


        val prod= Product(0,"EXAMPLE","EXAMPLE","EXAMPLE",0,null,0.0)
        myProductDatabase!!.getProductDao().addProduct(prod)
        myProductDatabase!!.getProductDao().deleteProduct(0)


        //Database deletion
        //applicationContext.deleteDatabase("Database")

        //Cast some objects into the database
        //Delete when done

        super.onCreate(savedInstanceState)
        var viewModel= ViewModelProvider(this).get(ProductViewModel::class.java)

        enableEdgeToEdge()
        setContent {
            TestFinalNowTheme{
                var hasNotificationPermission by remember {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                }
                val permissionLauncher= rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        hasNotificationPermission= isGranted
                    }
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding), myProductDatabase, viewModel)//*SUCCEEDED
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }



}

