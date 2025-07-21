package com.froginc.voiceassistant.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// For simplicity, using a Map for request/response; customize as needed.
interface OllamaService {

    @POST("api/v1/stream")
    suspend fun requestLLM(@Body body: Map<String, String>): Map<String, Any>

    companion object {
        fun create(): OllamaService {
            return Retrofit.Builder()
                .baseUrl("http://127.0.0.1:11434/") // Adjust if needed
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OllamaService::class.java)
        }
    }
}