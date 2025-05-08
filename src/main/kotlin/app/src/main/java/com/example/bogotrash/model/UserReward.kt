package com.example.bogotrash.model

import java.util.Date;

data class UserReward(
    val userId: Int,
    val rewardId: Int,
    val redeemedAt: Date
)
