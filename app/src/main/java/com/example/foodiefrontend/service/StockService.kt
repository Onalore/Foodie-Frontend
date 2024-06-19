package com.example.foodiefrontend.service

import com.example.foodiefrontend.data.EanResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StockService {
    @GET("escaner/ean/{ean}")
    fun findProductByEan(@Path("ean") ean: String): Call<EanResponse>
}
