package com.example.foodiefrontend.service

import com.example.foodiefrontend.data.AuthResponse
import com.example.foodiefrontend.data.LoginRequest
import com.example.foodiefrontend.data.RegisterResponse
import com.example.foodiefrontend.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("usuarios")
    fun registerUser(@Body user: User): Call<RegisterResponse>

    @POST("usuarios/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<AuthResponse>
}
