package com.example.foodiefrontend.service

import com.example.foodiefrontend.data.AuthResponse
import com.example.foodiefrontend.data.LoginRequest
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.data.RegisterResponse
import com.example.foodiefrontend.data.Restricciones
import com.example.foodiefrontend.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {
    @POST("usuarios")
    fun registerUser(@Body user: User): Call<RegisterResponse>

    @POST("usuarios/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<AuthResponse>

    @GET("comensales")
    fun getFamilyMembers(@Header("Authorization") token: String): Call<List<Persona>>

    @POST("comensales")
    fun addFamilyMember(
        @Header("Authorization") token: String,
        @Body persona: Persona
    ): Call<Persona>

    @PUT("comensales")
    fun updateFamilyMember(
        @Header("Authorization") token: String,
        @Body persona: Persona
    ): Call<Void>

    @DELETE("comensales")
    fun deleteFamilyMember(
        @Header("Authorization") token: String,
        @Query("nombre") nombre: String,
        @Query("apellido") apellido: String
    ): Call<Void>

    @PUT("usuarios/update/restrictions")
    fun actualizarRestriccionesUsuario(
        @Header("Authorization") token: String,
        @Body restricciones: Restricciones
    ): Call<Void>

}
