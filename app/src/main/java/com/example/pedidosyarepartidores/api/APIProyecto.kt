package com.example.pedidosyarepartidores.api


import com.example.pedidosyarepartidores.models.dto.LocationRequest
import com.example.pedidosyarepartidores.models.dto.LoginRequestDTO
import com.example.pedidosyarepartidores.models.dto.LoginResponseDTO
import com.example.pedidosyarepartidores.models.dto.OrderRequest
import com.example.pedidosyarepartidores.models.dto.Pedido
import com.example.pedidosyarepartidores.models.dto.Producto
import com.example.pedidosyarepartidores.models.dto.RegisterRequest
import com.example.pedidosyarepartidores.models.dto.Restaurant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface APIProyecto {

    // Autenticación
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequestDTO): LoginResponseDTO

    @POST("users")
    suspend fun register(@Body registerRequest: RegisterRequest): LoginResponseDTO


    // Restaurantes
    @POST("restaurants")
    suspend fun insertRestaurant(@Header("Authorization") token: String, @Body restaurant: Restaurant): Restaurant

    @GET("restaurants")
    suspend fun getRestaurants(@Header("Authorization") token: String): List<Restaurant>

    @GET("restaurants/{id}")
    suspend fun getRestaurantById(@Header("Authorization") token: String, @Path("id") id: Int): Restaurant


    // Productos
    @GET("restaurants/{id}/products")
    suspend fun getProductsByRestaurant(@Header("Authorization") token: String, @Path("id") restaurantId: Int): List<Producto>

    @POST("products")
    suspend fun createProduct(@Header("Authorization") token: String, @Body product: Producto): Response<Unit>

    // Pedidos
    @GET("orders")
    suspend fun getOrders(@Header("Authorization") token: String): List<Pedido>

    @POST("orders")
    suspend fun createOrder(@Header("Authorization") token: String, @Body request: OrderRequest): Response<Unit>

    @GET("orders/{id}")
    suspend fun getOrderDetails(@Header("Authorization") token: String, @Path("id") orderId: Int): Pedido


    @POST("orders/{id}/accept")
    suspend fun acceptOrder(@Header("Authorization") token: String, @Path("id") orderId: Int): Response<Unit>


    // Choferes
    @POST("drivers/location")
    suspend fun updateDriverLocation(@Header("Authorization") token: String, @Body location: LocationRequest): Unit

    @GET("orders/free")
    suspend fun getFreeOrders(@Header("Authorization") token: String): List<Pedido>

    @POST("drivers/location")
    suspend fun updateDriverLocation(@Body location: LocationRequest): Response<Unit>

    @POST("orders/{id}/onmyway")
    suspend fun markOrderOnTheWay(@Header("Authorization") token: String, @Path("id") orderId: Int): Response<Unit>

    @POST("orders/{id}/delivered")
    suspend fun markOrderDelivered(@Header("Authorization") token: String, @Path("id") orderId: Int): Response<Unit>



}

