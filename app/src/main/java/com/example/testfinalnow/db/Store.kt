package com.example.testfinalnow.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class Store(
    @PrimaryKey(autoGenerate = true)
    var storeId: Int,
    var Products: Int,
    var storeAddress: String
)