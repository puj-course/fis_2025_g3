package com.example.bogotrash

import org.junit.Test
import kotlin.test.assertEquals

class ProfileLogicTest {

    private fun getLevelAndProgress(points: Int): Pair<String, Int> {
        val level = points / 100
        val progress = points % 100
        val name = when {
            level >= 5 -> "Eco-Maestro"
            level >= 3 -> "Eco-Experto"
            level >= 1 -> "Eco-Iniciado"
            else -> "Eco-Novato"
        }
        return name to progress
    }

    @Test
    fun testGetLevelAndProgress() {
        assertEquals("Eco-Novato" to 40, getLevelAndProgress(40))
        assertEquals("Eco-Iniciado" to 25, getLevelAndProgress(125))
        assertEquals("Eco-Experto" to 80, getLevelAndProgress(380))
        assertEquals("Eco-Maestro" to 5, getLevelAndProgress(505))
    }
}
