package com.example.httptest.network

import retrofit2.Retrofit




fun retrofitBuilder(block: (Retrofit.Builder) -> Retrofit.Builder): Retrofit.Builder {
    return block(Retrofit.Builder())
}