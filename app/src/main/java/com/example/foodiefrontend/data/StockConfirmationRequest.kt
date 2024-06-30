package com.example.foodiefrontend.data

data class StockConfirmationRequest(
    val ean: String?,
    val tipoProducto: String,
    val cantidad: Int,
    val unidad: String,
    val alerta: Int,
    val unidadMedida: String,
)