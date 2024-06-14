package com.example.foodiefrontend.data

data class Recipe(
    val title: String,
    val rating: Int = 0,
    val imageUrl: String,
    val liked: Boolean = false,
    val ingredients: List<Ingredient> = SampleData.sampleIngredients,
    val preparation: List<String> = SampleData.samplePreparation,
    val timeOfPrep: Int = 0
)
