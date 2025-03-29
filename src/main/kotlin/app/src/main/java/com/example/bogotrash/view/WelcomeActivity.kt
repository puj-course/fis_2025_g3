package com.example.bogotrash.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.SessionManager

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar si hay una sesión activa
        val sessionManager = SessionManager(this)
        if (sessionManager.isLoggedIn()) {
            // Si hay sesión, ir directamente a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerrar WelcomeActivity para que no se pueda volver atrás
            return
        }

        // Si no hay sesión, mostrar la pantalla de bienvenida
        setContentView(R.layout.activity_welcome)

        // Configurar el botón de Registro
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Configurar el botón de Inicio de Sesión
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}