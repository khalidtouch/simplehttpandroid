package com.example.httptest.network.di

import com.example.httptest.network.AuthService
import com.example.httptest.network.retrofitBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://api.rechargedeck.com"



object NetworkModule {
    fun getAuthService(): AuthService = retrofitBuilder {
        it.baseUrl(BASE_URL)
            .addConverterFactory(
                providesNetworkJson.asConverterFactory("application/json".toMediaType())
            )
            .callFactory(okHttpCallFactory)
    }
        .build().create(AuthService::class.java)


    private val okHttpCallFactory: Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
        ).build()

    private val providesNetworkJson: Json = Json {
        ignoreUnknownKeys = true
    }

}