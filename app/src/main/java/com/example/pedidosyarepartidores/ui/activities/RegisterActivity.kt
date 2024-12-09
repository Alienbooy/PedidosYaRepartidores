package com.example.pedidosyarepartidores.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pedidosyarepartidores.databinding.ActivityRegisterBinding
import com.example.pedidosyarepartidores.ui.viewmodels.RegisterViewModel


class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels() // viewmodel para el registro

    // sirve para mostrar mensajes de error o exito en el registro
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        viewModel.registrationSuccess.observe(this) { // observa si el registro fue exitoso
            if (it) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show() // muestro mensaje de exito
                finish()
            }
        }

        viewModel.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}