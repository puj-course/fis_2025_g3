package com.example.bogotrash

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RegisterValidationTest {

    private fun isUserValid(name: String, email: String, password: String): Boolean {
        return name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
    }

    private fun isRecyclerValid(phone: String, address: String, zone: String): Boolean {
        return phone.isNotBlank() && address.isNotBlank() && zone.isNotBlank()
    }

    @Test
    fun testValidUser() {
        assertTrue(isUserValid("Juan", "juan@example.com", "1234"))
    }

    @Test
    fun testInvalidUserMissingFields() {
        assertFalse(isUserValid("", "email", ""))
    }

    @Test
    fun testValidRecycler() {
        assertTrue(isRecyclerValid("123", "calle 1", "norte"))
    }

    @Test
    fun testInvalidRecyclerMissingFields() {
        assertFalse(isRecyclerValid("", "dir", ""))
    }
}
