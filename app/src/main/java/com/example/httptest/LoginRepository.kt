package com.example.httptest

import android.util.Log
import com.example.httptest.network.AuthService
import com.example.httptest.network.NetworkResult
import com.example.httptest.network.di.NetworkModule
import com.example.httptest.network.getResponse
import com.example.httptest.utils.LoginRequest
import com.example.httptest.utils.LoginResponse
import retrofit2.awaitResponse

interface LoginRepository {
    suspend fun login(request: LoginRequest): NetworkResult<LoginResponse>
}


class TestLoginRepository(
    private val authService: AuthService = NetworkModule.getAuthService()
) : LoginRepository {
    override suspend fun login(request: LoginRequest): NetworkResult<LoginResponse> = runCatching {
        val response = authService.login(request)

        Log.e("Repo", "login: the raw response is ${response.getResponse()}")

        if (response.isSuccessful) {
            val res = requireNotNull(response.getResponse())

            if (res.status.contains("success", ignoreCase = true)) {
                return NetworkResult.success(
                    (LoginResponse(
                        status = res.status,
                        token = res.token,
                        message = res.message,
                    ))
                )
            } else {
                return NetworkResult.failed(
                    (LoginResponse(
                        status = res.status,
                        token = res.token,
                        message = res.message,
                    ))
                )
            }

        } else {
            return NetworkResult.error("Something went wrong")
        }

    }.getOrDefault(NetworkResult.error("Error logging in"))

}