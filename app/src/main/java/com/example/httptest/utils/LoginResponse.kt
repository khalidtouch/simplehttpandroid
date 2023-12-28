package com.example.httptest.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LoginResponse(
    @SerialName("status") val status: String = "",
    @SerialName("result") val token: String = "",
    @SerialName("message") val message: String = "",
)