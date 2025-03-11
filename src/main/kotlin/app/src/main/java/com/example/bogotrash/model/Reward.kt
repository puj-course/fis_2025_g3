package com.example.bogotrash.model

data class Reward(
    val id: Int,
    val points: Int,
    val description: String, // Ej. "Descuento en tienda"
    val redemptionCode: String? = null
)