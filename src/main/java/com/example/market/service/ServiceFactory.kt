package com.example.market.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {
    fun <T> createRetrofitService(clazz: Class<T>, endPoint: String): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(endPoint)
            .build()
        return retrofit.create(clazz)
    }
}
