package com.example.bogotrash

import org.junit.Assert.*
import org.junit.Test

class RankingAdapterTest {

    private fun getMedal(position: Int): String {
        return when (position) {
            0 -> "🥇 "
            1 -> "🥈 "
            2 -> "🥉 "
            else -> ""
        }
    }

    @Test
    fun medalIsCorrect() {
        assertEquals("🥇 ", getMedal(0))
        assertEquals("🥈 ", getMedal(1))
        assertEquals("🥉 ", getMedal(2))
        assertEquals("", getMedal(3))
    }
}
