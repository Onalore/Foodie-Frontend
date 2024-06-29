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
        "Intolerancia a la Lactosa",
        "Ninguna"
    )

    val comidas = listOf(
        "Desayuno",
        "Almuerzo",
        "Merienda",
        "Cena"
    )

    val categories = listOf(
        "Favoritas" to { },
        "Historial" to { },
        "Creadas" to { }
    )
}