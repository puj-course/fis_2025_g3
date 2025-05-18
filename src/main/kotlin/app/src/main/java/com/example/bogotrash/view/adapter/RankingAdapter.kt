package com.example.bogotrash.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bogotrash.R
import com.example.bogotrash.model.UserRanking

class RankingAdapter(private val rankings: List<UserRanking>) :
    RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.rank_user_name)
        val userLevel: TextView = itemView.findViewById(R.id.rank_user_level)
        val userPoints: TextView = itemView.findViewById(R.id.rank_user_points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val user = rankings[position]

        // Medallitas
        val medal = when (position) {
            0 -> "🥇 "
            1 -> "🥈 "
            2 -> "🥉 "
            else -> ""
        }

        holder.userName.text = "$medal${user.name}"
        holder.userLevel.text = user.level
        holder.userPoints.text = "${user.points} pts"
    }

    override fun getItemCount() = rankings.size
}
