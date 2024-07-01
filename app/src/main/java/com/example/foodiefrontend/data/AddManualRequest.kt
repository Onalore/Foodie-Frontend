package com.example.foodiefrontend.data

data class AddManualRequest(
    val nombreProducto: String,
    val cantidad: Int,
    val unidad: Int,
    val alerta: Int
)