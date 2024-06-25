package com.example.foodiefrontend.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("id") val id: String? = null,
    @SerializedName("description") val description: String = "Banana",
    @SerializedName("quantity") val quantity: String = "",
    @SerializedName("unit") val unit: String = "",
    @SerializedName("imageUrl") val imageUrl: String = ""
)
