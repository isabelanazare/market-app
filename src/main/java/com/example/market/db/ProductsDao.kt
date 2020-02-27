package com.example.market.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.market.domain.Product

@Dao
interface ProductsDao {

    @get:Query("select * from products")
    val products: LiveData<MutableList<Product>>

    @get:Query("select count(*) from products")
    val numberOfProducts: Int

    @Insert
    fun addProduct(product: Product)

    @Insert
    fun addProducts(products: List<Product>)

    @Delete
    fun deleteProduct(p: Product)

    @Query("delete from products")
    fun deleteProducts()

    @Update
    fun updateProduct(p: Product)
}
