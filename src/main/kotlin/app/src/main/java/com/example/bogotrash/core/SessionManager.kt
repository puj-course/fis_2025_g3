package com.example.bogotrash.core

import android.content.Context
import android.content.SharedPreferences

class SessionManager private constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("BogoTrashPrefs", Context.MODE_PRIVATE)

    companion object {
        private lateinit var appContext: Context

        val instance: SessionManager by lazy {
            SessionManager(appContext)
        }

        fun init(context: Context) {
            appContext = context.applicationContext
        }

        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USER_EMAIL = "userEmail"
    }

    fun saveSession(email: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
