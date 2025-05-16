package com.example.bogotrash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogotrash.model.Reward
import com.example.bogotrash.repository.DatabaseConnection
import kotlin.concurrent.thread
import java.sql.Connection

class RewardViewModel : ViewModel() {

    private val _rewards = MutableLiveData<List<Reward>>()
    val rewards: LiveData<List<Reward>> get() = _rewards

    private val _userPoints = MutableLiveData<Int>()
    val userPoints: LiveData<Int> get() = _userPoints

    fun loadRewards() {
        thread {
            try {
                val conn: Connection? = DatabaseConnection.getConnection()
                val rewardList = mutableListOf<Reward>()
                conn?.use { connection ->
                    val stmt = connection.prepareStatement("SELECT id, title, description, points FROM Rewards")
                    val rs = stmt.executeQuery()
                    while (rs.next()) {
                        rewardList.add(
                            Reward(
                                id = rs.getInt("id"),
                                name = rs.getString("title"),
                                description = rs.getString("description"),
                                points = rs.getInt("points"),
                                imageName = "reward_${rs.getInt("id")}"
                            )
                        )
                    }
                    rs.close()
                    stmt.close()
                }
                _rewards.postValue(rewardList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUserPoints(email: String) {
        thread {
            try {
                val conn: Connection? = DatabaseConnection.getConnection()
                conn?.use { connection ->
                    val stmt = connection.prepareStatement("SELECT total_points FROM Users WHERE email = ?")
                    stmt.setString(1, email)
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        _userPoints.postValue(rs.getInt("total_points"))
                    }
                    rs.close()
                    stmt.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
