package com.example.pedidosyarepartidores.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosyarepartidores.databinding.ItemOrderBinding
import com.example.pedidosyarepartidores.models.dto.Pedido
import androidx.recyclerview.widget.DiffUtil

class OrderAdapter(
    private val onOrderClick: (Pedido) -> Unit
) : ListAdapter<Pedido, OrderAdapter.OrderViewHolder>(DiffCallback) {

    class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        // Función que se encarga de actualizar la vista con los datos del pedido cuando se inicia sesion
        fun bind(order: Pedido, onClick: (Pedido) -> Unit) {
            binding.tvOrderId.text = "Pedido #${order.id}"
            binding.tvNameOrder.text = order.address ?: "Nombre no disponible"
            binding.tvOrderDate.text = "Fecha: ${order.fechaHora}"
            binding.tvOrderAddress.text = "Dirección: ${order.address ?: "No definida"}"
            binding.tvOrderTotal.text = "Total: ${order.total} Bs"
            binding.root.setOnClickListener { onClick(order) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position), onOrderClick)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pedido>() {
        override fun areItemsTheSame(oldItem: Pedido, newItem: Pedido): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Pedido, newItem: Pedido): Boolean = oldItem == newItem
    }
}


