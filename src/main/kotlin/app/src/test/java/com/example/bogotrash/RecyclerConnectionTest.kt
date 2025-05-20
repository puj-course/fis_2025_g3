package com.example.bogotrash

import com.example.bogotrash.model.Recycler
import org.junit.Test
import kotlin.test.assertEquals

class RecyclerConnectionTest {

    private val recyclers = listOf(
        Recycler(1, "Juan", "123", "Dir A", "Norte"),
        Recycler(2, "Ana", "456", "Dir B", "Centro"),
        Recycler(3, "Carlos", "789", "Dir C", "Sur")
    )

    private fun filterRecyclerList(query: String): List<Recycler> {
        return recyclers.filter {
            it.name.contains(query, ignoreCase = true) || it.zone.contains(query, ignoreCase = true)
        }
    }

    @Test
    fun testMatchByName() {
        val result = filterRecyclerList("ana")
        assertEquals(1, result.size)
        assertEquals("Ana", result[0].name)
    }

    @Test
    fun testMatchByZone() {
        val result = filterRecyclerList("sur")
        assertEquals(1, result.size)
        assertEquals("Carlos", result[0].name)
    }

    @Test
    fun testNoMatch() {
        val result = filterRecyclerList("bogota")
        assertEquals(0, result.size)
    }
}
