package com.example.pedidosyarepartidores.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosyarepartidores.R
import com.example.pedidosyarepartidores.databinding.ActivityFreeOrdersBinding
import com.example.pedidosyarepartidores.databinding.ItemOrderBinding
import com.example.pedidosyarepartidores.ui.viewmodels.OrderViewModel

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ItemOrderBinding
    private val viewModel: OrderViewModel by viewModels()
    // Muestra los detalles del pedido
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getIntExtra("ORDER_ID", -1)
        if (orderId != -1) {
            viewModel.loadOrderDetails(orderId)
        }

        setupObservers()
        //setupActions(orderId)
    }
    // Observa los detalles del pedido cuando se da click en un pedido
    private fun setupObservers() {
        viewModel.orderDetails.observe(this) { order ->
            binding.tvOrderId.text = "Pedido #${order.id}"
            binding.tvNameOrder.text = order.address ?: "Nombre no disponible"
            binding.tvOrderAddress.text = "Dirección: ${order.address ?: "No disponible"}"
            binding.tvOrderTotal.text = "Total: ${order.total} Bs"
        }
    }
    /*
    private fun setupActions(orderId: Int) {
        binding.btnAccept.setOnClickListener {
            viewModel.acceptOrder(orderId)
            //navigateToStatus(orderId)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

     */

    /*
    private fun navigateToStatus(orderId: Int) {
        val intent = Intent(this, OrderStatusActivity::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
        finish()
    }

     */
}



