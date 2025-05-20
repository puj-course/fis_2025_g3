package com.example.bogotrash

import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class RewardCanjeLogicTest {

    data class Reward(val id: Int, val points: Int)

    private fun canRedeem(userPoints: Int, reward: Reward, alreadyRedeemed: Boolean): Boolean {
        if (alreadyRedeemed) return false
        if (userPoints < reward.points) return false
        return true
    }

    @Test
    fun `can redeem reward if enough points and not already redeemed`() {
        val reward = Reward(1, 50)
        assertTrue(canRedeem(100, reward, false))
    }

    @Test
    fun `cannot redeem reward if already redeemed`() {
        val reward = Reward(1, 50)
        assertFalse(canRedeem(100, reward, true))
    }

    @Test
    fun `cannot redeem reward if not enough points`() {
        val reward = Reward(1, 100)
        assertFalse(canRedeem(50, reward, false))
    }
}
