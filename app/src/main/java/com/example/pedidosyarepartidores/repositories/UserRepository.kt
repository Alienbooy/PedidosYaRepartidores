package com.example.pedidosyarepartidores.repositories

import android.content.Context
import android.util.Log
import com.example.pedidosyarepartidores.api.APIProyecto
import com.example.pedidosyarepartidores.models.dto.LoginRequestDTO
import com.example.pedidosyarepartidores.models.dto.LoginResponseDTO
import com.example.pedidosyarepartidores.models.dto.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.log

object UserRepository {

    /**
     * Manejo de inicio de sesión
     */
    suspend fun doLogin(
        email: String,
        password: String,
        context: Context,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        // se obtiene la instancia de Retrofit y se crea una instancia de la interfaz APIProyecto
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        // se realiza la petición de inicio de sesión
        withContext(Dispatchers.IO) {
            try {
                val response = service.login(LoginRequestDTO(email, password))
                success(response)
            } catch (t: Throwable) {
                Log.e("UserRepository", "Error al iniciar sesión: ${t.message}")
                failure(t)
            }
        }
    }

    /**
     * Registro de usuario
     */
    // esta función se encarga de registrar un usuario en las data class de la API
    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        role: Int,
        context: Context,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        // se obtiene la instancia de Retrofit y se crea una instancia de la interfaz APIProyecto
        val retrofit = RetrofitRepository.getRetrofitInstance(context)
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        try {
            // se realiza la petición de registro del usuario
            val response = withContext(Dispatchers.IO) {
                service.register(RegisterRequest(name, email, password, role))
            }
            Log.d("UserRepository", "Registro exitoso: $response")
            success(response)
        } catch (t: Throwable) {
            Log.e("UserRepository", "Error al registrar usuario: ${t.message}")
            failure(t)
        }
    }

}
