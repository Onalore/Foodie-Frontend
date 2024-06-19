package com.example.foodiefrontend.data

data class User(
    val mail: String,
    val password: String,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val restricciones: List<String>
)
