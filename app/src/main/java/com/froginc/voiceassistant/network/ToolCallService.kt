package com.froginc.voiceassistant.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ToolCallService {

    @POST("api/tool")
    suspend fun callTool(@Body body: Map<String, String>): Map<String, Any>

    companion object {
        fun create(): ToolCallService {
            return Retrofit.Builder()
                .baseUrl("http://192.168.0.100/") // Replace with your Pico W's LAN IP address
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ToolCallService::class.java)
        }
    }
}