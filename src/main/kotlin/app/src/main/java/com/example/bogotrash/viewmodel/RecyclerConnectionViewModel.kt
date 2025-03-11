package com.example.bogotrash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogotrash.model.Recycler

class RecyclerConnectionViewModel : ViewModel() {
    private val _recyclers = MutableLiveData<List<Recycler>>()
    val recyclers: LiveData<List<Recycler>> get() = _recyclers

    fun loadRecyclers() {
        val recyclerList = listOf(
            Recycler(1, "Recicladores del Sur", "123456789", "Calle 10 # 5-50", "Barrio Sur")
        )
        _recyclers.value = recyclerList
    }
}