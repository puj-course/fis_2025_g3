package com.example.bogotrash.model

data class Recycler(
    val id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val serviceArea: String // Ej. "Barrio X"
)