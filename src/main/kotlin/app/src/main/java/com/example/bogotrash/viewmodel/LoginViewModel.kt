package com.example.bogotrash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bogotrash.repository.UserRepository

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _isRecycler = MutableLiveData<Boolean>()
    val isRecycler: LiveData<Boolean> get() = _isRecycler

    fun login(email: String, password: String) {
        Thread {
            val result = UserRepository.loginUser(email, password)
            _loginResult.postValue(result)

            if (result) {
                val recycler = UserRepository.isRecycler(email)
                _isRecycler.postValue(recycler)
            }
        }.start()
    }
}
