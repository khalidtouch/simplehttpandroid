package com.example.httptest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.httptest.network.AuthService
import com.example.httptest.network.di.NetworkModule
import com.example.httptest.utils.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository = TestLoginRepository(),
    private val authService: AuthService = NetworkModule.getAuthService(),
) : ViewModel() {


    val successState = MutableStateFlow(false)
    val message = MutableStateFlow("")
    val username = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun setUsername(name: String) {
        username.value = name
    }

    fun setPassword(pass: String) {
        password.value = pass
    }


    fun login() {
        viewModelScope.launch {
            val res = loginRepository.login(
                LoginRequest(
                    username = username.value,
                    password = password.value,
                )
            )

            Log.e("ViewModel", "login: response is $res", )
            res.onSuccess {
                successState.value = true
                message.value = it.status
            }
            res.onFailure {
                successState.value = false
                message.value = it.message
            }
            res.onError {
                successState.value = false
                message.value = it
            }
        }
    }
}