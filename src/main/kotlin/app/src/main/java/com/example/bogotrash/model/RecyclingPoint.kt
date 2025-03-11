package com.example.bogotrash.model

data class RecyclingPoint(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val type: String // Ej. "Contenedor", "Recolección Electrónica"
)