package com.example.bogotrash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bogotrash.viewmodel.RewardViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.awaitility.kotlin.*
import java.util.concurrent.TimeUnit

class RewardViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `loadRewards should eventually populate rewards LiveData`() {
        val viewModel = RewardViewModel()

        // Ejecuta la función real ya que nos conectamos directamente a la BD en el ViewModel y no hay
        // Manera de Mockearlo :(
        viewModel.loadRewards()

        // Espera hasta que el LiveData tenga un valor, con timeout
        await.atMost(5, TimeUnit.SECONDS).until {
            viewModel.rewards.value != null && viewModel.rewards.value!!.isNotEmpty()
        }

        val rewards = viewModel.rewards.value!!
        assertTrue(rewards.isNotEmpty())
        println("Primer reward: ${rewards.first().name}")
    }
}
