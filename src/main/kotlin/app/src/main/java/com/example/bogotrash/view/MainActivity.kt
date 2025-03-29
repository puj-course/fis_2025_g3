package com.example.bogotrash.view

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        wasteGuideViewModel = ViewModelProvider(this)[WasteGuideViewModel::class.java]
        recyclerViewModel = ViewModelProvider(this)[RecyclerConnectionViewModel::class.java]
        rewardViewModel = ViewModelProvider(this)[RewardViewModel::class.java]

        // Cargar datos iniciales
        mapViewModel.loadRecyclingPoints()
        wasteGuideViewModel.loadWasteTypes()
        recyclerViewModel.loadRecyclers()
        rewardViewModel.loadRewards()

        // Configurar el ImageButton
        val logoButton = findViewById<ImageButton>(R.id.logoButton)
        logoButton.setOnClickListener {
            Toast.makeText(this, "Logo presionado!", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar más lógica, como navegar a otra pantalla
        }

        // Observar cambios (ejemplo con MapViewModel)
        mapViewModel.recyclingPoints.observe(this) { points ->
            // Actualizar UI (implementar en el layout)
        }
    }
}