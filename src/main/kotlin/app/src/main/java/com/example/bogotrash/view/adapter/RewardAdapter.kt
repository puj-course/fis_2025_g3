package com.example.bogotrash.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bogotrash.R
import com.example.bogotrash.model.Reward

class RewardAdapter(private val rewards: List<Reward>) :
    RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {

    class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.rewardIcon)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val pointsTextView: TextView = itemView.findViewById(R.id.pointsTextView)
        val redeemButton: Button = itemView.findViewById(R.id.redeemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reward, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]
        holder.iconImageView.setImageResource(reward.iconResId)
        holder.nameTextView.text = reward.name
        holder.pointsTextView.text = "Canjeable por ${reward.points} puntos"
        holder.redeemButton.setOnClickListener {
            // Lógica para canjear (a implementar, por ejemplo, restar puntos al usuario)
            // Por ahora, solo muestra un mensaje
            android.widget.Toast.makeText(holder.itemView.context, "Canjeando ${reward.name}", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = rewards.size
}