package com.example.foodiefrontend.service

import com.example.foodiefrontend.data.EanResponse
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.StockResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StockService {
    @GET("escaner/ean/{ean}")
    fun findProductByEan(@Path("ean") ean: String): Call<EanResponse>
    @GET("stock")
    fun getUserStock(): Call<StockResponse>
    @POST("ean")
    fun addProductByEan(@Body eanRequest: Map<String, Any>): Call<Void>
    @GET("stock/ingredients")
    fun getStockIngredients(): Call<List<Ingredient>>
}
