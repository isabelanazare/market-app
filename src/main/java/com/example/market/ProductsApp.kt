package com.example.market

import android.app.Application
import androidx.room.Room

import com.example.market.db.ProductDatabase

class ProductsApp : Application() {
    lateinit var db: ProductDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder<ProductDatabase>(applicationContext,
            ProductDatabase::class.java, "database-name").build()
    }
}
