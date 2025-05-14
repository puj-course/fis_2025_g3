package com.example.bogotrash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bogotrash.databinding.ActivityRewardsBinding
import com.example.bogotrash.model.Reward
import com.example.bogotrash.view.adapter.RewardAdapter
import androidx.core.view.WindowCompat


class RewardsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        binding = ActivityRewardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Datos simulados (reemplazar con datos de la base de datos)
        val rewards = listOf(
            Reward(1,60, "Bonificación Semillas","Semillas de amistad", "SEEDS2025", android.R.drawable.ic_menu_gallery), // Placeholder icon
            Reward(2,150, "Descuento en Transporte","un descuento en transporte publico", "TRANS2025", android.R.drawable.ic_menu_gallery), // Placeholder icon
            Reward(3,300, "EcoBotella", "Una botella de agua", "BOTTLE2025", android.R.drawable.ic_menu_gallery) // Placeholder icon
        )

        // Configurar RecyclerView
        binding.recyclerViewRewards.apply {
            layoutManager = LinearLayoutManager(this@RewardsActivity)
            adapter = RewardAdapter(rewards)
        }

    }
}