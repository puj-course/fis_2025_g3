package com.example.bogotrash.view.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.bogotrash.R
import com.example.bogotrash.model.Reward
import com.example.bogotrash.repository.DatabaseConnection
import com.example.bogotrash.viewmodel.RewardViewModel
import java.sql.Connection
import kotlin.concurrent.thread

class RewardAdapter(
    private val rewards: List<Reward>,
    private val viewModel: RewardViewModel,
    private val userEmail: String
) : RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {

    class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.rewardIcon)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val pointsTextView: TextView = itemView.findViewById(R.id.pointsTextView)
        val redeemButton: Button = itemView.findViewById(R.id.redeemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reward, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]
        val context = holder.itemView.context

        val imageResId = context.resources.getIdentifier(
            reward.imageName, "drawable", context.packageName
        )
        holder.iconImageView.setImageResource(if (imageResId != 0) imageResId else R.drawable.regalo)

        holder.nameTextView.text = reward.name
        holder.pointsTextView.text = "Canjeable por ${reward.points} puntos"

        holder.redeemButton.setOnClickListener {
            thread {
                try {
                    val conn: Connection? = DatabaseConnection.getConnection()
                    conn?.use { connection ->

                        // Obtener id y puntos
                        val stmt = connection.prepareStatement("SELECT id, total_points FROM Users WHERE email = ?")
                        stmt.setString(1, userEmail)
                        val rs = stmt.executeQuery()

                        if (rs.next()) {
                            val userId = rs.getInt("id")
                            val points = rs.getInt("total_points")

                            // Verificar si ya fue canjeado
                            val checkStmt = connection.prepareStatement(
                                "SELECT COUNT(*) FROM UserRewards WHERE user_id = ? AND reward_id = ?"
                            )
                            checkStmt.setInt(1, userId)
                            checkStmt.setInt(2, reward.id)
                            val rsCheck = checkStmt.executeQuery()
                            rsCheck.next()
                            val alreadyRedeemed = rsCheck.getInt(1) > 0

                            if (alreadyRedeemed) {
                                showToast(context, "Ya has canjeado esta recompensa")
                                rsCheck.close()
                                checkStmt.close()
                                return@thread
                            }

                            if (points < reward.points) {
                                showToast(context, "No tienes suficientes puntos")
                                rsCheck.close()
                                checkStmt.close()
                                return@thread
                            }

                            // Insertar canje
                            val insertStmt = connection.prepareStatement(
                                "INSERT INTO UserRewards (user_id, reward_id) VALUES (?, ?)"
                            )
                            insertStmt.setInt(1, userId)
                            insertStmt.setInt(2, reward.id)
                            insertStmt.executeUpdate()

                            // Descontar puntos
                            val updateStmt = connection.prepareStatement(
                                "UPDATE Users SET total_points = ? WHERE id = ?"
                            )
                            updateStmt.setInt(1, points - reward.points)
                            updateStmt.setInt(2, userId)
                            updateStmt.executeUpdate()

                            showToast(context, "¡${reward.name} canjeada!")
                            viewModel.updateUserPoints(userEmail)

                            insertStmt.close()
                            updateStmt.close()
                            rsCheck.close()
                            checkStmt.close()
                        }

                        rs.close()
                        stmt.close()
                    }
                } catch (e: Exception) {
                    showToast(context, "Error al canjear: ${e.message}")
                }
            }
        }
    }

    override fun getItemCount(): Int = rewards.size

    private fun showToast(context: Context, message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

