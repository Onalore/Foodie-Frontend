package com.example.foodiefrontend.data

object SampleData {
    val image = "https://lacocinadevero.com/wp-content/uploads/2021/02/carne-con-papas-1024x680.jpg"

    val sampleIngredients = listOf(
        Ingredient(id = "Espinaca", cantidad = "1", unidad = "u.", imageUrl = ""),
        Ingredient(id = "Tomate cherry", cantidad = "1/4", unidad = "kg", imageUrl = ""),
        Ingredient(id = "Huevo", cantidad = "3", unidad = "u.", imageUrl = ""),
        Ingredient(id = "Garbanzos", cantidad = "", unidad = "", imageUrl = ""),
        Ingredient(id = "Queso", cantidad = "1/4", unidad = "kg", imageUrl = "")
    )

    val sampleIngredient = Ingredient(id = "Espinaca", cantidad = "1", unidad = "u.", imageUrl = "")

    val samplePreparation = listOf(
        "Mezcle en una fuente la harina, el aceite, las zanahorias, la carne de soya hidratada, la cebolla y la sal",
        "Agregue agua mientras mezcla, deberá obtener una masa compacta.",
        "Coloque sobre una lata aceitada.",
        "Lleve al horno por 30min.",
        "Sirva frío."
    )

    val recipes = listOf(
        Recipe("Bombas de papa", 3, image, true),
        Recipe("Canelones", 2, image),
        Recipe("Vegetarianas", 0, image, true)
    )


    val recipe = Recipe(
        name = "Bondiolita a la cerveza",
        imageUrl = "https://www.clarin.com/2021/01/20/uqrDLM9BM_600x600__1.jpg",
        ingredients = sampleIngredients,
        preparation = samplePreparation
    )

}