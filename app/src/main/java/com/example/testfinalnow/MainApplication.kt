package com.example.testfinalnow

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.example.testfinalnow.db.ProductDatabase




class MainApplication: Application() {

    companion object{
        lateinit var productDatabase: ProductDatabase
    }

    override fun onCreate() {
        super.onCreate()
        productDatabase= Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "Product"
        ).build()

        val channel = NotificationChannel(
            "channel_id",
            "Channel_name",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }




}
