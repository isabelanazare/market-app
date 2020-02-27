package com.example.market.service

import com.example.market.domain.Product
import retrofit2.http.*


interface ProductService {

    @GET("/products")
    suspend fun getProducts(): List<Product>

    @POST("/product")
    suspend fun addProduct(@Body e: Product): Product

    companion object {
        const val SERVICE_ENDPOINT = "http://10.0.2.2:2024"
    }
}
