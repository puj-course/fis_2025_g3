package com.example.bogotrash

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LoginValidationTest {

    private fun validateLoginFields(email: String, password: String): Boolean {
        return email.trim().isNotEmpty() && password.trim().isNotEmpty()
    }

    @Test
    fun testValidLogin() {
        assertTrue(validateLoginFields("user@example.com", "1234"))
    }

    @Test
    fun testEmptyFields() {
        assertFalse(validateLoginFields("", ""))
        assertFalse(validateLoginFields("   ", "   "))
    }

    @Test
    fun testTrimmedInput() {
        assertTrue(validateLoginFields(" user@example.com ", " 1234 "))
    }
}
