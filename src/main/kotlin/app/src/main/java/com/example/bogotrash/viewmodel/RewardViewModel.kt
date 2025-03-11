package com.example.bogotrash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogotrash.model.Reward

class RewardViewModel : ViewModel() {
    private val _rewards = MutableLiveData<List<Reward>>()
    val rewards: LiveData<List<Reward>> get() = _rewards

    private val _userPoints = MutableLiveData<Int>(0)
    val userPoints: LiveData<Int> get() = _userPoints

    fun addPoints(points: Int) {
        _userPoints.value = (_userPoints.value ?: 0) + points
    }

    fun loadRewards() {
        val rewardList = listOf(
            Reward(1, 100, "Descuento 10% en EcoTienda", "ECO10")
        )
        _rewards.value = rewardList
    }
}