package com.example.bogotrash.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val passwordHash: String,
    val totalPoints: Int = 0
)
