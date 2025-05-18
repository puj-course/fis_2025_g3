package com.example.bogotrash.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bogotrash.core.SessionManager
import com.example.bogotrash.databinding.ActivityRewardsBinding
import com.example.bogotrash.viewmodel.RewardViewModel
import com.example.bogotrash.view.adapter.RewardAdapter
import androidx.core.view.WindowCompat

class RewardsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardsBinding
    private val viewModel: RewardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        binding = ActivityRewardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = SessionManager.instance.getUserEmail()
        if (email != null) {
            viewModel.updateUserPoints(email)
            viewModel.loadRewards()
        }

        binding.recyclerViewRewards.layoutManager = LinearLayoutManager(this)

        viewModel.rewards.observe(this, Observer { rewards ->
            binding.recyclerViewRewards.adapter = RewardAdapter(rewards, viewModel, email!!)
        })
    }
}

