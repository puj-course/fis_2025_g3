package com.example.bogotrash.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bogotrash.R
import com.example.bogotrash.model.UserRanking
import com.example.bogotrash.repository.DatabaseConnection
import kotlin.concurrent.thread
import com.example.bogotrash.view.adapter.RankingAdapter

class RankingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val recyclerView = findViewById<RecyclerView>(R.id.ranking_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        thread {
            try {
                val conn = DatabaseConnection.getConnection()
                val rankingList = mutableListOf<UserRanking>()

                val query = """
                    SELECT name, total_points FROM Users
                    ORDER BY total_points DESC
                    LIMIT 20
                """.trimIndent()

                val stmt = conn?.prepareStatement(query)
                val rs = stmt?.executeQuery()

                while (rs != null && rs.next()) {
                    val name = rs.getString("name")
                    val points = rs.getInt("total_points")
                    val level = when {
                        points >= 500 -> "Eco-Maestro"
                        points >= 300 -> "Eco-Experto"
                        points >= 100 -> "Eco-Iniciado"
                        else -> "Eco-Novato"
                    }
                    rankingList.add(UserRanking(name, level, points))
                }

                runOnUiThread {
                    recyclerView.adapter = RankingAdapter(rankingList)
                }

                rs?.close()
                stmt?.close()
                conn?.close()

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error al cargar ranking: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

