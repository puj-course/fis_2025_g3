package com.example.bogotrash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogotrash.model.WasteType

class WasteGuideViewModel : ViewModel() {
    private val _wasteTypes = MutableLiveData<List<WasteType>>()
    val wasteTypes: LiveData<List<WasteType>> get() = _wasteTypes

    fun loadWasteTypes() {
        val wasteList = listOf(
            WasteType(1, "Plástico", true, "Separar envases y enjuagarlos"),
            WasteType(2, "Orgánico", false, "No reciclable, compostar si es posible")
        )
        _wasteTypes.value = wasteList
    }
}