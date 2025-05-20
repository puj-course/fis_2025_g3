package com.example.bogotrash

import com.google.android.gms.maps.model.LatLng
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapActivityTest {

    private fun parseLatLng(location: String): LatLng? {
        val parts = location.split(",")
        if (parts.size != 2) return null
        val lat = parts[0].toDoubleOrNull()
        val lng = parts[1].toDoubleOrNull()
        return if (lat != null && lng != null) LatLng(lat, lng) else null
    }

    @Test
    fun testValidLatLng() {
        val result = parseLatLng("4.7110,-74.0721")
        assertEquals(4.7110, result?.latitude)
        assertEquals(-74.0721, result?.longitude)
    }

    @Test
    fun testInvalidLatLngFormat() {
        val result = parseLatLng("invalid")
        assertNull(result)
    }

    @Test
    fun testNonNumericLatLng() {
        val result = parseLatLng("abc,xyz")
        assertNull(result)
    }
}
