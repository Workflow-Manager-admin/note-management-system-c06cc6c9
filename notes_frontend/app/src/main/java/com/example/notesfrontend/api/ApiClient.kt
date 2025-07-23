package com.example.notesfrontend.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // You should set the BASE_URL to your backend address (e.g., "http://10.0.2.2:5000/" for local dev on emulator)
    private const val BASE_URL = "http://10.0.2.2:5000"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
