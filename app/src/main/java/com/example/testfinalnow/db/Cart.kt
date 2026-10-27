package com.example.testfinalnow.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cart",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )])
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var product: String,
    var price: Double,
    var productId: Int
) {

}