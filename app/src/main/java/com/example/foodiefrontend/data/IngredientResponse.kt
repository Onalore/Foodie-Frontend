package com.example.foodiefrontend.data

import com.google.gson.annotations.SerializedName

data class IngredientResponse(
    val id: String,
    val unidadMedida: String,
    val cantidad: Int,
    val unidad: String,
    @SerializedName("imageUrl") val imageUrl: String = "",
)
