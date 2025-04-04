package com.example.bogotrash.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.SessionManager
import com.example.bogotrash.repository.DatabaseConnection
import com.example.bogotrash.repository.UserRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.sql.Connection
import kotlin.concurrent.thread

class ProfileActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializar SessionManager
        sessionManager = SessionManager(this)

        // Obtener referencias a los elementos de la UI
        val userNameTextView = findViewById<TextView>(R.id.user_name)
        val userLevelTextView = findViewById<TextView>(R.id.user_level)
        val levelProgress = findViewById<LinearProgressIndicator>(R.id.level_progress)
        val progressText = findViewById<TextView>(R.id.progress_text)
        val recycledWasteTextView = findViewById<TextView>(R.id.recycled_waste)
        val placesVisitedTextView = findViewById<TextView>(R.id.places_visited)
        val rewardsRedeemedTextView = findViewById<TextView>(R.id.rewards_redeemed)
        val logoutButton = findViewById<MaterialButton>(R.id.logout_button)

        // Configurar el botón de logout
        logoutButton.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Obtener el email del usuario actual
        val userEmail = sessionManager.getUserEmail()
        if (userEmail == null) {
            Toast.makeText(this, "Error: No hay sesión activa", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Cargar datos del usuario desde la base de datos en un hilo separado
        thread {
            try {
                val conn: Connection? = DatabaseConnection.getConnection()
                conn?.use { connection ->
                    // Consulta para obtener datos del usuario
                    val userQuery = """
                        SELECT name, total_points 
                        FROM Users 
                        WHERE email = ?
                    """.trimIndent()
                    val userStmt = connection.prepareStatement(userQuery)
                    userStmt.setString(1, userEmail)
                    val userRs = userStmt.executeQuery()

                    if (userRs.next()) {
                        val name = userRs.getString("name")
                        val totalPoints = userRs.getInt("total_points")

                        // Calcular nivel y progreso (ejemplo simple basado en puntos)
                        val level = totalPoints / 100 // Cada 100 puntos sube un nivel
                        val progress = (totalPoints % 100) // Progreso al siguiente nivel
                        val levelName = when {
                            level >= 5 -> "Eco-Maestro"
                            level >= 3 -> "Eco-Experto"
                            level >= 1 -> "Eco-Iniciado"
                            else -> "Eco-Novato"
                        }

                        // Consulta para estadísticas (asumiendo tablas adicionales)
                        val statsQuery = """
                            SELECT 
                                (SELECT COALESCE(SUM(weight), 0) FROM RecyclingRecords WHERE user_id = u.id) as recycled_waste,
                                (SELECT COUNT(*) FROM RecyclingPointVisits WHERE user_id = u.id) as places_visited,
                                (SELECT COUNT(*) FROM UserRewards WHERE user_id = u.id) as rewards_redeemed
                            FROM Users u
                            WHERE u.email = ?
                        """.trimIndent()
                        val statsStmt = connection.prepareStatement(statsQuery)
                        statsStmt.setString(1, userEmail)
                        val statsRs = statsStmt.executeQuery()

                        var recycledWaste = 0f
                        var placesVisited = 0
                        var rewardsRedeemed = 0
                        if (statsRs.next()) {
                            recycledWaste = statsRs.getFloat("recycled_waste")
                            placesVisited = statsRs.getInt("places_visited")
                            rewardsRedeemed = statsRs.getInt("rewards_redeemed")
                        }

                        // Actualizar UI en el hilo principal
                        runOnUiThread {
                            userNameTextView.text = name
                            userLevelTextView.text = levelName
                            levelProgress.progress = progress
                            progressText.text = "$progress% hacia el siguiente nivel"
                            recycledWasteTextView.text = String.format("%.1f kg", recycledWaste)
                            placesVisitedTextView.text = placesVisited.toString()
                            rewardsRedeemedTextView.text = rewardsRedeemed.toString()
                        }

                        statsRs.close()
                        statsStmt.close()
                    } else {
                        runOnUiThread {
                            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    }

                    userRs.close()
                    userStmt.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error al cargar datos: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}