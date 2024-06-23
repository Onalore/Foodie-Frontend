package com.example.foodiefrontend.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BackendApi {

    private val BASE_URL = Config.getBaseUrl()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .build()

    fun createRecipesService(): RecipesService {
        return retrofit.create(RecipesService::class.java)
    }

    fun createStockService(): StockService {
        return retrofit.create(StockService::class.java)
    }

    fun createUserService(): UserService {
        return retrofit.create(UserService::class.java)
    }

}