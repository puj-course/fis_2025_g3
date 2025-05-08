package com.example.bogotrash.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.bogotrash.R
import com.example.bogotrash.core.SessionManager
import com.example.bogotrash.viewmodel.MapViewModel
import com.example.bogotrash.viewmodel.WasteGuideViewModel
import com.example.bogotrash.viewmodel.RecyclerConnectionViewModel
import com.example.bogotrash.viewmodel.RewardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var wasteGuideViewModel: WasteGuideViewModel
    private lateinit var recyclerViewModel: RecyclerConnectionViewModel
    private lateinit var rewardViewModel: RewardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_main)

        // Inicializar ViewModels
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        wasteGuideViewModel = ViewModelProvider(this)[WasteGuideViewModel::class.java]
        recyclerViewModel = ViewModelProvider(this)[RecyclerConnectionViewModel::class.java]
        rewardViewModel = ViewModelProvider(this)[RewardViewModel::class.java]

        // Cargar datos iniciales
        mapViewModel.loadRecyclingPoints()
        wasteGuideViewModel.loadWasteTypes()
        recyclerViewModel.loadRecyclers()
        rewardViewModel.loadRewards()

        // Configurar clics en las tarjetas
        findViewById<CardView>(R.id.mapCard).setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        findViewById<CardView>(R.id.wasteGuideCard).setOnClickListener {
            startActivity(Intent(this, WasteGuideActivity::class.java))
        }

        findViewById<CardView>(R.id.recyclerConnectionCard).setOnClickListener {
            startActivity(Intent(this, RecyclerConnectionActivity::class.java))
        }

        findViewById<CardView>(R.id.rewardsCard).setOnClickListener {
            startActivity(Intent(this, RewardsActivity::class.java))
        }

        // Configurar clics en las secciones
        findViewById<LinearLayout>(R.id.learnMoreSection).setOnClickListener {
            startActivity(Intent(this, LearnMoreActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.campaignSection).setOnClickListener {
            startActivity(Intent(this, CampaignActivity::class.java))
        }

        // Configurar la barra de navegación inferior
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Ya estamos en MainActivity
                    true
                }
                R.id.nav_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
                    true
                }
                R.id.nav_rewards -> {
                    startActivity(Intent(this, RewardsActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}