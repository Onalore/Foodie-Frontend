package com.example.foodiefrontend.data

data class Recipe(
    val title: String,
    val initialRating: Int,
    val image: Int,
    val liked: Boolean = false
)
