package com.example.bogotrash.model

data class WasteType(
    val id: Int,
    val name: String,
    val recyclable: Boolean,
    val description: String,
    val imageUrl: String? = null // Para ejemplos prácticos
)