package com.example.pedidosyarepartidores.repositories

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {
    fun getRetrofitInstance(context: Context): Retrofit {
        val client = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val original = chain.request()
                val token = PreferencesRepository.getToken(context)
                val request = original.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl("https://proyectodelivery.jmacboy.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
