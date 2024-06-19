package com.example.foodiefrontend.data

data class Ingredient(
    val name: String,
    val quantity: String,
    val unit: String,
    val imageUrl: String = "https://www.svgrepo.com/show/146075/question.svg"
)
