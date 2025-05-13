package com.example.bogotrash.model

data class Reward(
    val id: Int,
    val points: Int,
    val name: String,
    val description: String, // Ej. "Descuento en tienda"
    val redemptionCode: String? = null,
    val iconResId: Int // Nuevo campo para el ID del recurso de la imagen
)