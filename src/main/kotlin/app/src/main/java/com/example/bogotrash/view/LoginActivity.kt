package com.example.bogotrash.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.SessionManager

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulación de inicio de sesión con valores quemados
            val existingUserEmail = "user@example.com"
            val existingUserPassword = "password123"

            // Integrar con MySQL
            // hacer una solicitud a tu backend para verificar las credenciales
            // Ejemplo con pseudocódigo:
            // val response = MySQLClient.loginUser(email, password)
            // if (response.isSuccess) {
            //     sessionManager.saveSession(email)
            //     navigateToMainActivity()
            // } else {
            //     Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            // }

            // Simulación: Verificar las credenciales
            if (email == existingUserEmail && password == existingUserPassword) {
                // Guardar la sesión
                val sessionManager = SessionManager(this)
                sessionManager.saveSession(email)

                // Navegar a MainActivity
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Cerrar LoginActivity
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}