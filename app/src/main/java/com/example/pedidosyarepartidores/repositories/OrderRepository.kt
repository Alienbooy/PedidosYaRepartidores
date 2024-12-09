package com.example.pedidosyarepartidores.repositories

import android.content.Context
import com.example.pedidosyarepartidores.api.APIProyecto
import com.example.pedidosyarepartidores.models.dto.LocationRequest
import com.example.pedidosyarepartidores.models.dto.Pedido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object OrderRepository {

    private fun getApi(context: Context): APIProyecto {
        return RetrofitRepository.getRetrofitInstance(context).create(APIProyecto::class.java)
    }

    suspend fun getFreeOrders(context: Context): List<Pedido> {
        val token = "Bearer ${PreferencesRepository.getToken(context)}"
        return getApi(context).getFreeOrders(token)
    }

    suspend fun getOrderDetails(context: Context, orderId: Int): Pedido {
        val token = "Bearer ${PreferencesRepository.getToken(context)}"
        return getApi(context).getOrderDetails(token, orderId)
    }

    suspend fun acceptOrder(context: Context, orderId: Int): Boolean {
        val token = "Bearer ${PreferencesRepository.getToken(context)}"
        return getApi(context).acceptOrder(token, orderId).isSuccessful
    }

    suspend fun markOrderOnTheWay(context: Context, orderId: Int): Boolean {
        val token = "Bearer ${PreferencesRepository.getToken(context)}"
        return getApi(context).markOrderOnTheWay(token, orderId).isSuccessful
    }

    suspend fun markOrderDelivered(context: Context, orderId: Int): Boolean {
        val token = "Bearer ${PreferencesRepository.getToken(context)}"
        return getApi(context).markOrderDelivered(token, orderId).isSuccessful
    }
}








