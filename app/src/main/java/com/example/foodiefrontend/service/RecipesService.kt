package com.example.foodiefrontend.service

import com.example.foodiefrontend.data.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface RecipesService {
    @GET("gemini/?q=carne%20papas%20morron%20cebolla")
    fun getRecipes(): Call<List<Recipe>>
}
