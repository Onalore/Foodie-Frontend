
package com.example.foodiefrontend.data

object SampleData {
    val image = "https://lacocinadevero.com/wp-content/uploads/2021/02/carne-con-papas-1024x680.jpg"

    val sampleIngredients = listOf(
        Ingredient(id = "Espinaca", quantity = "1", unit = "u.", imageUrl = ""),
        Ingredient(id = "Tomate cherry", quantity = "1/4", unit = "kg", imageUrl = ""),
        Ingredient(id = "Huevo", quantity = "3", unit = "u.", imageUrl = ""),
        Ingredient(id = "Garbanzos", quantity = "", unit = "", imageUrl = ""),
        Ingredient(id = "Queso", quantity = "1/4", unit = "kg", imageUrl = "")
    )

    val sampleIngredient = Ingredient(id = "Espinaca", quantity = "1", unit = "u.", imageUrl = "")

    val samplePreparation = listOf(
        "Mezcle en una fuente la harina, el aceite, las zanahorias, la carne de soya hidratada, la cebolla y la sal",
        "Agregue agua mientras mezcla, deberá obtener una masa compacta.",
        "Coloque sobre una lata aceitada.",
        "Lleve al horno por 30min.",
        "Sirva frío."
    )

    val recipes = listOf(
        Recipe("Bombas de papa", 3, image, true, listOf(), listOf()),
        Recipe("Canelones", 2, image, true, listOf(), listOf()),
        Recipe("Vegetarianas", 0, image, true, listOf(), listOf())
    )


    val recipe = Recipe(
        name = "Bondiolita a la cerveza",
        imageUrl = "https://www.clarin.com/2021/01/20/uqrDLM9BM_600x600__1.jpg",
        ingredients = sampleIngredients,
        preparation = samplePreparation
    )

    val listOfPersona = listOf(
        Persona(
            nombre = "María",
            apellido = "García",
            edad = 25,
            restricciones = listOf("Intolerancia a la lactosa", "Diabetes")
        ),
        Persona(
            nombre = "Carlos",
            apellido = "Rodríguez",
            edad = 40,
            restricciones = listOf("Hipertensión")
        ),
        Persona(
            nombre = "Ana",
            apellido = "Martínez",
            edad = 35,
            restricciones = listOf("Kosher", "Vegetarianismo")
        ),
        Persona(
            nombre = "Luis",
            apellido = "González",
            edad = 28,
            restricciones = listOf("Embarazo")
        )
    )
}
