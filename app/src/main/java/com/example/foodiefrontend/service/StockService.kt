package com.example.foodiefrontend.service

import com.example.foodiefrontend.data.EanResponse
import com.example.foodiefrontend.data.IngredientResponse
import com.example.foodiefrontend.data.StockConfirmationRequest
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

    @GET("ean/{ean}")
    fun obtenerTipoProductoPorEAN(@Path("ean") ean: String): Call<EanResponse>

    @POST("stock/confirmation")
    fun confirmation(
        @Header("Authorization") token: String,
        @Body stockConfirmationRequest: StockConfirmationRequest
    ): Call<Void>

    @POST("stock/manual")
    fun addProductByName(
        @Body productRequest: Map<String, Any>,
        @Header("Authorization") token: String
    ): Call<Void>
}
