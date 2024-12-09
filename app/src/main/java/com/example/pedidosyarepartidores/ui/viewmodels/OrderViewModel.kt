package com.example.pedidosyarepartidores.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.pedidosyarepartidores.models.dto.Pedido
import com.example.pedidosyarepartidores.repositories.OrderRepository
import kotlinx.coroutines.launch
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    // Uso del singleton directamente
    private val repository = OrderRepository

    // LiveData para pedidos libres
    private val _freeOrders = MutableLiveData<List<Pedido>>()
    val freeOrders: LiveData<List<Pedido>> get() = _freeOrders

    // LiveData para detalles de un pedido
    private val _orderDetails = MutableLiveData<Pedido>()
    val orderDetails: LiveData<Pedido> get() = _orderDetails

    // LiveData para errores
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    // Método para cargar pedidos libres
    fun fetchFreeOrders(context: Context) {
        viewModelScope.launch {
            try {
                val orders = repository.getFreeOrders(context)
                _freeOrders.postValue(orders)
            } catch (e: Exception) {
                _errorMessage.postValue("Error al cargar los pedidos: ${e.message}")
            }
        }
    }
    fun selectOrder(order: Pedido) {
        _orderDetails.postValue(order)
    }


    // Método para cargar detalles de un pedido
    fun loadOrderDetails(orderId: Int) {
        viewModelScope.launch {
            try {
                val order = repository.getOrderDetails(getApplication(), orderId)
                _orderDetails.postValue(order)
            } catch (e: Exception) {
                _errorMessage.postValue("Error al cargar los detalles del pedido: ${e.message}")
            }
        }
    }

    // Método para aceptar un pedido
    fun acceptOrder(orderId: Int) {
        viewModelScope.launch {
            try {
                val success = repository.acceptOrder(getApplication(), orderId)
                if (success) {
                    _errorMessage.postValue("Pedido aceptado exitosamente.")
                } else {
                    _errorMessage.postValue("Error al aceptar el pedido.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error al aceptar el pedido: ${e.message}")
            }
        }
    }

    // Método para marcar un pedido como "en camino"
    fun markOrderOnTheWay(orderId: Int) {
        viewModelScope.launch {
            try {
                val success = repository.markOrderOnTheWay(getApplication(), orderId)
                if (success) {
                    _errorMessage.postValue("El pedido está en camino.")
                } else {
                    _errorMessage.postValue("Error al actualizar el estado del pedido.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error al actualizar el estado: ${e.message}")
            }
        }
    }

    // Método para marcar un pedido como "entregado"
    fun markOrderDelivered(orderId: Int) {
        viewModelScope.launch {
            try {
                val success = repository.markOrderDelivered(getApplication(), orderId)
                if (success) {
                    _errorMessage.postValue("Pedido entregado correctamente.")
                } else {
                    _errorMessage.postValue("Error al marcar el pedido como entregado.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error al entregar el pedido: ${e.message}")
            }
        }
    }
}

