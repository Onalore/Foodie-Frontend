package com.example.foodiefrontend.service

import FavoriteRecipesResponse
import TemporaryRecipeResponse
import com.example.foodiefrontend.data.DinersData
import com.example.foodiefrontend.data.RatingData
import com.example.foodiefrontend.data.Recipe
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RecipesService {
    @POST("recetas/user")
    fun getRecipes(
        @Header("Authorization") token: String,
        @Body dinersData: DinersData
    ): Call<List<Recipe>>

    @POST("recetas/random")
    fun randomRecipes(
        @Header("Authorization") token: String,
        @Body dinersData: DinersData
    ): Call<List<Recipe>>

    @POST("recetas/guardar")
    fun saveTemporaryRecipe(
        @Header("Authorization") token: String,
        @Body receta: Recipe,
    ): Call<Unit>
    @GET("recetas/ver")
    suspend fun getTemporaryRecipe(@Header("Authorization") token: String): Response<TemporaryRecipeResponse>

    @DELETE("recetas/borrar")
    suspend fun deleteTemporaryRecipe(@Header("Authorization") token: String): Response<Void>

    @POST("recetas/puntuar")
    suspend fun rateRecipe(
        @Header("Authorization") token: String,
        @Body ratingData: RatingData
    ): Response<Unit>

    @GET("recetas/favoritas")
    suspend fun getFavoriteRecipes(@Header("Authorization") token: String): Response<FavoriteRecipesResponse>

    @GET("recetas/creadas")
    suspend fun getCreatedRecipes(@Header("Authorization") token: String): Response<FavoriteRecipesResponse>

    @GET("recetas/historial")
    suspend fun getHistoryRecipes(@Header("Authorization") token: String): Response<FavoriteRecipesResponse>

    @POST("recetas/custom")
    fun createRecipe(
        @Header("Authorization") token: String,
        @Body receta: Recipe
    ): Call<Unit>
}
