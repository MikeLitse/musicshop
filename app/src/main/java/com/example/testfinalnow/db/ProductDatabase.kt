package com.example.testfinalnow.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= [Product::class, Cart::class, Store::class, StoreItems::class] , version = 1)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object{
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase?{

            val tempInstance= INSTANCE // val to check if an instance already exists
            if(tempInstance != null){  // if it exists, return the existent instance and don't make a new one
                return tempInstance
            }

            if(INSTANCE == null){
                synchronized(ProductDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ProductDatabase::class.java, "Database") // CHANGE THE NAME IN ORDER TO CREATE A NEW DATABASE
                        .allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }

    }

}