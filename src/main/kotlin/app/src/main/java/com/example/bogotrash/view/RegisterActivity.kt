package com.example.bogotrash.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.SessionManager

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulación de registro con valores quemados
            val existingUserEmail = "user@example.com"
            val existingUserPassword = "password123"

            //  Integrar con MySQL
            // hacer una solicitud a tu backend para verificar si el email ya está registrado
            // pseudocódigo:
            // val response = MySQLClient.checkUserExists(email)
            // if (response.isUserExists) {
            //     Toast.makeText(this, "El email ya está registrado", Toast.LENGTH_SHORT).show()
            //     return@setOnClickListener
            // }
            // MySQLClient.registerUser(email, password)

            // Simulación: Verificar si el email ya está "registrado"
            if (email == existingUserEmail) {
                Toast.makeText(this, "El email ya está registrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulación: "Registrar" el usuario
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

            // Guardar la sesión
            val sessionManager = SessionManager(this)
            sessionManager.saveSession(email)

            // Navegar a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerrar RegisterActivity
        }
    }
}