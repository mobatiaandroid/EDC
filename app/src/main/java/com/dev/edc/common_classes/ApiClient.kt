package com.dev.edc.common_classes

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private lateinit var apiService: ApiService
    fun getApiService(): ApiService {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-35-153-18-27.compute-1.amazonaws.com/edc/public/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        apiService = retrofit.create(ApiService::class.java)
        return apiService
    }
}