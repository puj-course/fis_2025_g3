package com.example.bogotrash

import org.junit.Test
import kotlin.test.assertEquals

class RankingLevelTest {

    private fun getLevel(points: Int): String {
        return when {
            points >= 500 -> "Eco-Maestro"
            points >= 300 -> "Eco-Experto"
            points >= 100 -> "Eco-Iniciado"
            else -> "Eco-Novato"
        }
    }

    @Test
    fun testEcoNovato() {
        assertEquals("Eco-Novato", getLevel(99))
    }

    @Test
    fun testEcoIniciado() {
        assertEquals("Eco-Iniciado", getLevel(150))
    }

    @Test
    fun testEcoExperto() {
        assertEquals("Eco-Experto", getLevel(350))
    }

    @Test
    fun testEcoMaestro() {
        assertEquals("Eco-Maestro", getLevel(600))
    }
}
