package com.example.foodiefrontend.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RecipesApi {

    //En emulador 10.0.2.2 = localhost
    private val BASE_URL = "http://10.0.2.2:8080/api/gemini/"

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

    fun create(): RecipesService {
        return retrofit.create(RecipesService::class.java)
    }

}