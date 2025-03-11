package com.example.bogotrash.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.bogotrash.R
import com.example.bogotrash.viewmodel.MapViewModel
import com.example.bogotrash.viewmodel.WasteGuideViewModel
import com.example.bogotrash.viewmodel.RecyclerConnectionViewModel
import com.example.bogotrash.viewmodel.RewardViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var wasteGuideViewModel: WasteGuideViewModel
    private lateinit var recyclerViewModel: RecyclerConnectionViewModel
    private lateinit var rewardViewModel: RewardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar ViewModels
        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        wasteGuideViewModel = ViewModelProvider(this).get(WasteGuideViewModel::class.java)
        recyclerViewModel = ViewModelProvider(this).get(RecyclerConnectionViewModel::class.java)
        rewardViewModel = ViewModelProvider(this).get(RewardViewModel::class.java)

        // Cargar datos iniciales
        mapViewModel.loadRecyclingPoints()
        wasteGuideViewModel.loadWasteTypes()
        recyclerViewModel.loadRecyclers()
        rewardViewModel.loadRewards()

        // Observar cambios (ejemplo con MapViewModel)
        mapViewModel.recyclingPoints.observe(this, { points ->
            // Actualizar UI (implementar en el layout)
        })
    }
}