package com.example.bogotrash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogotrash.model.RecyclingPoint

class MapViewModel : ViewModel() {
    private val _recyclingPoints = MutableLiveData<List<RecyclingPoint>>()
    val recyclingPoints: LiveData<List<RecyclingPoint>> get() = _recyclingPoints

    fun loadRecyclingPoints() {
        // View model sin utilizar
        val points = listOf(
            RecyclingPoint(1, "Punto Reciclaje Centro", 4.7110, -74.0721, "Contenedor"),
            RecyclingPoint(2, "Recolección Electrónica Norte", 4.7500, -74.0500, "Recolección Electrónica")
        )
        _recyclingPoints.value = points
    }
}