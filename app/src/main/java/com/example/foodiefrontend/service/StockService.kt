package com.example.foodiefrontend.service

import com.example.foodiefrontend.data.EanResponse
import com.example.foodiefrontend.data.IngredientResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface StockService {
    @GET("escaner/ean/{ean}")
    fun findProductByEan(@Path("ean") ean: String): Call<EanResponse>

    @GET("stock")
    fun getUserStock(@Header("Authorization") token: String): Call<List<IngredientResponse>>

    @POST("ean")
    fun addProductByEan(@Body eanRequest: Map<String, Any>, s: String): Call<Void>

    @POST("stock/manual")
    fun addProductByName(
        @Body productRequest: Map<String, Any>,
        @Header("Authorization") token: String
    ): Call<Void>
}
