package com.example.bogotrash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bogotrash.databinding.ActivityRecyclerConnectionBinding
import com.example.bogotrash.model.Recycler

class RecyclerConnectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerConnectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Datos simulados (reemplazar con datos de la base de datos)
        val recyclers = listOf(
            Recycler(1, "Reciclador Juan", "3001234567", "Calle 123 #45-67", "Zona Centro"),
            Recycler(2, "Recicladora María", "3109876543", "Avenida 68 #12-34", "Zona Norte"),
            Recycler(3, "Reciclador Luis", "3204567890", "Carrera 7 #89-01", "Zona Sur"),
            Recycler(4, "Reciclador Pedro", "3015556677", "Carrera 15 #20-30", "Zona Este")
        )

        // Configurar RecyclerView
        binding.recyclerViewRecyclers.apply {
            layoutManager = LinearLayoutManager(this@RecyclerConnectionActivity)
            adapter = RecyclerAdapter(recyclers)
        }

        // conectar con la base de datos 
        /*
        val databaseConnection = DatabaseConnection(this)
        val recyclersFromDb = databaseConnection.getRecyclers()
        binding.recyclerViewRecyclers.apply {
            layoutManager = LinearLayoutManager(this@RecyclerConnectionActivity)
            adapter = RecyclerAdapter(recyclersFromDb)
        }
        */
    }
}