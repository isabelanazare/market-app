package com.example.market.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.market.domain.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductsDao
}
