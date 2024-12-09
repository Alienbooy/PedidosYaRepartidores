package com.example.pedidosyarepartidores.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pedidosyarepartidores.repositories.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    private val role = 2 // Rol fijo para los drivers

    private val _registrationSuccess = MutableLiveData(false)
    val registrationSuccess: LiveData<Boolean> get() = _registrationSuccess

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> get() = _errorMessage

    // hace el registro del usuario
    fun register() {
        // Validar campos antes de registrar
        val inputName = name.value ?: ""
        val inputEmail = email.value ?: ""
        val inputPassword = password.value ?: ""

        if (validateInputs(inputName, inputEmail, inputPassword)) {
            viewModelScope.launch {
                UserRepository.registerUser(
                    name = inputName,
                    email = inputEmail,
                    password = inputPassword,
                    role = role, // Siempre será rol de chofer
                    context = getApplication(),
                    success = {
                        if (it != null) {
                            _registrationSuccess.postValue(true)
                        } else {
                            _errorMessage.postValue("Error al registrar usuario")
                        }
                    },
                    failure = {
                        _errorMessage.postValue(it.message ?: "Error desconocido")
                    }
                )
            }
        }
    }

    private fun validateInputs(name: String, email: String, password: String): Boolean {
        return when {
            name.isBlank() -> {
                _errorMessage.value = "El nombre no puede estar vacío"
                false
            }
            email.isBlank() -> {
                _errorMessage.value = "El email no puede estar vacío"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _errorMessage.value = "El email no tiene un formato válido"
                false
            }
            password.length < 8 -> {
                _errorMessage.value = "La contraseña debe tener al menos 8 caracteres"
                false
            }
            else -> true
        }
    }
}
