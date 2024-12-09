package com.example.pedidosyarepartidores.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedidosyarepartidores.databinding.ActivityFreeOrdersBinding
import com.example.pedidosyarepartidores.repositories.OrderRepository
import com.example.pedidosyarepartidores.ui.adapters.OrderAdapter
import com.example.pedidosyarepartidores.ui.viewmodels.OrderViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FreeOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFreeOrdersBinding
    private val viewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()

        // Llamada para obtener los pedidos libres
        viewModel.fetchFreeOrders(this)
    }

    private fun setupRecyclerView() {
        binding.rvFreeOrders.layoutManager = LinearLayoutManager(this)
        binding.rvFreeOrders.adapter = OrderAdapter { order ->
            // Selecciona el pedido y lanza la actividad de detalle
            viewModel.selectOrder(order)
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("ORDER_ID", order.id)
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        // Observa la lista de pedidos disponibles
        viewModel.freeOrders.observe(this) { orders ->
            if (orders.isNullOrEmpty()) {
                Toast.makeText(this, "No hay pedidos disponibles.", Toast.LENGTH_SHORT).show()
            } else {
                (binding.rvFreeOrders.adapter as OrderAdapter).submitList(orders)
            }
        }

        // Observa errores y muestra mensajes
        viewModel.errorMessage.observe(this) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}


