package com.example.testfinalnow.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    var id: Int,
    var name: String,
    var type: String,
    var artist: String,
    var stock: Int,
    var image: String?,
    var price: Double
){
    fun returnProduct(): String{
        return artist+" - "+name
    }
}