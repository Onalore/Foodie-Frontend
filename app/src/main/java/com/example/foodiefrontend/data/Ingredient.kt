package com.example.foodiefrontend.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("id") val id: String? = null,
    @SerializedName("description") val description: String = "",
    @SerializedName("quantity") val quantity: String = "",
    @SerializedName("unit") val unit: String = "",
    @SerializedName("unitMesure") val unitMesure: String = "",
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("alertaEscasez") val alertaEscasez: Int = 0
)
