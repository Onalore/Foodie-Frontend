package com.example.foodiefrontend.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecipesApi {

    private val BASE_URL = "http://localhost:8080/api/gemini"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): RecipesService {
        return retrofit.create(RecipesService::class.java)
    }

}