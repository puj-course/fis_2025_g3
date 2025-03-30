package com.example.bogotrash.model

import java.util.Date

data class Participation(
    val userId: Int,
    val campaignId: Int,
    val joinedAt: Date
)