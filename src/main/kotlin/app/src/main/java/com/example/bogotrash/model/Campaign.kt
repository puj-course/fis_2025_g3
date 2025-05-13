package com.example.bogotrash.model

import java.util.Date

data class Campaign(
    val id: Int,
    val title: String,
    val description: String,
    val startDate: Date,
    val endDate: Date
)
