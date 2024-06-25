package com.example.foodiefrontend.service

import FavoriteRecipesResponse
import TemporaryRecipeResponse
import com.example.foodiefrontend.data.DinersData
import com.example.foodiefrontend.data.Recipe
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RecipesService {
    @GET("recetas/user")
    fun getRecipes(@Header("Authorization") token: String): Call<List<Recipe>>

    @GET("recetas/random")
    fun randomRecipes(@Header("Authorization") token: String): Call<List<Recipe>>

    @GET("recetas/favoritas")
    suspend fun getFavoriteRecipes(@Header("Authorization") token: String): Response<FavoriteRecipesResponse>

    @GET("recetas/ver")
    suspend fun getTemporaryRecipe(@Header("Authorization") token: String): Response<TemporaryRecipeResponse>

    //enviado informacion comensales
    @POST("recetas/info")
    suspend fun sendDinersData(
        @Header("Authorization") token: String,
        @Body data: DinersData
    ): Response<Unit>
}
