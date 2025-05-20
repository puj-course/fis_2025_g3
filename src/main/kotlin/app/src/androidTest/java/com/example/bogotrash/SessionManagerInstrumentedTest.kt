package com.example.bogotrash

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bogotrash.core.SessionManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class SessionManagerInstrumentedTest {

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        SessionManager.init(context)
    }

    @Test
    fun testSaveAndRetrieveSession() {
        val session = SessionManager.instance
        session.saveSession("user@bogotrash.com")

        assertTrue(session.isLoggedIn())
        assertEquals("user@bogotrash.com", session.getUserEmail())

        session.clearSession()
        assertNull(session.getUserEmail())
    }
}
