package com.example.bogotrash.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.core.SessionManager
import com.example.bogotrash.repository.DatabaseConnection
import com.example.bogotrash.repository.UserRepository
import androidx.core.view.WindowCompat

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_login)

        // Test de conexión para depurar
        Thread {
            try {
                val conn = DatabaseConnection.getConnection()
                val stmt = conn?.createStatement()
                val rs = stmt?.executeQuery("SELECT email FROM Users")

                while (rs != null && rs.next()) {
                    println("✅ Usuario encontrado: ${rs.getString("email")}")
                }

                rs?.close()
                stmt?.close()
                conn?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

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

            //Autenticación en la BD
            Thread {
                val success = UserRepository.loginUser(email, password)
                runOnUiThread {
                    if (success) {
                        val session = SessionManager.instance
                        session.saveSession(email)

                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                        // Verificar si es reciclador
                        Thread {
                            val isRecycler = UserRepository.isRecycler(email)
                            runOnUiThread {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("isRecycler", isRecycler)
                                startActivity(intent)
                                finish()
                            }
                        }.start()

                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }
}

