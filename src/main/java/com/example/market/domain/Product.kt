package com.example.market.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(@field:PrimaryKey(autoGenerate = true)
                    var id: Int, var name: String?, var description: String?, var quantity: Int?, var price: Int?, var status: String?)
