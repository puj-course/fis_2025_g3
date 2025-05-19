package com.example.bogotrash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bogotrash.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)


        
        // Placeholder para la lógica del mapa (a implementar)
        /*
        // Inicializar el mapa (ejemplo con Google Maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapPlaceholder) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            // Configurar el mapa aquí
            // Ejemplo: añadir marcadores
            val reciclajeLatLng = LatLng(4.7110, -74.0721) // Ejemplo: Bogotá
            googleMap.addMarker(MarkerOptions().position(reciclajeLatLng).title("Punto de reciclaje"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(reciclajeLatLng, 12f))
        }
        */

        // Botón "+" (a implementar)
        binding.addButton.setOnClickListener {
            // Lógica para añadir un nuevo punto de reciclaje
        }
    }
}