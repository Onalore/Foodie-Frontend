package com.example.foodiefrontend.utils

object Constants {
    val restricciones = listOf(
        "Celiaquía",
        "Embarazo",
        "Vegetarianismo",
        "Veganismo",
        "Diabetes",
        "Kosher",
        "Hipertensión",
        "Intolerancia a la Lactosa"
    )

    val comidas = listOf(
        "Desayuno",
        "Almuerzo",
        "Merienda",
        "Cena"
    )

    val categories = listOf(
        "Todas" to { },
        "Favoritas" to { },
        "Creadas" to { },
        "Historial" to { }
    )
}