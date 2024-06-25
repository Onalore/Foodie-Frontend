package com.example.foodiefrontend.data

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("name") val name: String = "",
    @SerializedName("puntuacion") val rating: Int = 0,
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("liked") val liked: Boolean = false,
    @SerializedName("ingredients") val ingredients: List<Ingredient> = listOf(),
    @SerializedName("steps") val preparation: List<String> = listOf(),
    @SerializedName("timeOfPrep") val timeOfPrep: Int = 0
)
