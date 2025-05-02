package com.example.bogotrash.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.R
import com.example.bogotrash.SessionManager
import com.example.bogotrash.repository.DatabaseConnection
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.sql.Connection
import kotlin.concurrent.thread

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val session = SessionManager.instance

        val userNameTextView = findViewById<TextView>(R.id.user_name)
        val userLevelTextView = findViewById<TextView>(R.id.user_level)
        val levelProgress = findViewById<LinearProgressIndicator>(R.id.level_progress)
        val progressText = findViewById<TextView>(R.id.progress_text)
        val totalPointsTextView = findViewById<TextView>(R.id.total_points)  // <- NUEVO
        val participationsTextView = findViewById<TextView>(R.id.campaigns_participated)
        val rewardsRedeemedTextView = findViewById<TextView>(R.id.rewards_redeemed)
        val logoutButton = findViewById<MaterialButton>(R.id.logout_button)

        logoutButton.setOnClickListener {
            session.clearSession()
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val userEmail = session.getUserEmail()
        if (userEmail == null) {
            Toast.makeText(this, "Error: No hay sesión activa", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        thread {
            try {
                val conn: Connection? = DatabaseConnection.getConnection()
                conn?.use { connection ->

                    val stmtUser = connection.prepareStatement("""
                        SELECT id, name, total_points 
                        FROM Users 
                        WHERE email = ?
                    """.trimIndent())
                    stmtUser.setString(1, userEmail)
                    val rsUser = stmtUser.executeQuery()

                    if (rsUser.next()) {
                        val userId = rsUser.getInt("id")
                        val name = rsUser.getString("name")
                        val totalPoints = rsUser.getInt("total_points")

                        val level = totalPoints / 100
                        val progress = totalPoints % 100
                        val levelName = when {
                            level >= 5 -> "Eco-Maestro"
                            level >= 3 -> "Eco-Experto"
                            level >= 1 -> "Eco-Iniciado"
                            else -> "Eco-Novato"
                        }

                        val stmtStats = connection.prepareStatement("""
                            SELECT 
                                (SELECT COUNT(*) FROM Participations WHERE user_id = ?) AS participations,
                                (SELECT COUNT(*) FROM UserRewards WHERE user_id = ?) AS rewards
                        """.trimIndent())
                        stmtStats.setInt(1, userId)
                        stmtStats.setInt(2, userId)
                        val rsStats = stmtStats.executeQuery()

                        var participations = 0
                        var rewardsRedeemed = 0
                        if (rsStats.next()) {
                            participations = rsStats.getInt("participations")
                            rewardsRedeemed = rsStats.getInt("rewards")
                        }

                        runOnUiThread {
                            userNameTextView.text = name
                            userLevelTextView.text = levelName
                            levelProgress.progress = progress
                            progressText.text = "$progress% hacia el siguiente nivel"
                            totalPointsTextView.text = totalPoints.toString()
                            participationsTextView.text = participations.toString()
                            rewardsRedeemedTextView.text = rewardsRedeemed.toString()
                        }

                        rsStats.close()
                        stmtStats.close()
                    }

                    rsUser.close()
                    stmtUser.close()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error al cargar perfil: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
