package com.example.bogotrash

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("BogoTrashPrefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USER_EMAIL = "userEmail"
    }

    // Guardar sesión
    fun saveSession(email: String) {
        val editor = prefs.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_EMAIL, email)
        editor.apply()
    }

    // Verificar si hay una sesión activa
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Obtener el email del usuario logueado
    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    // Cerrar sesión
    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}