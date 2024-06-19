package com.example.foodiefrontend.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.AuthResponse
import com.example.foodiefrontend.data.LoginRequest
import com.example.foodiefrontend.data.RegisterResponse
import com.example.foodiefrontend.data.User
import com.example.foodiefrontend.service.BackendApi
import com.example.foodiefrontend.service.UserService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val apiService: UserService = BackendApi.createUserService()

    private val _loginResult = MutableLiveData<AuthResponse?>()
    val loginResult: LiveData<AuthResponse?> = _loginResult

    private val _registerResult = MutableLiveData<RegisterResponse?>()
    val registerResult: LiveData<RegisterResponse?> = _registerResult

    fun loginUser(mail: String, password: String) {
        val loginRequest = LoginRequest(mail = mail, password = password)
        viewModelScope.launch {
            apiService.loginUser(loginRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        _loginResult.value = response.body()
                    } else {
                        _loginResult.value = null
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    _loginResult.value = null
                }
            })
        }
    }

    fun registerUser(mail: String, password: String, nombre: String, apellido: String, edad: Int, restricciones: List<String>, onResult: (RegisterResponse?) -> Unit) {
        val user = User(mail, password, nombre, apellido, edad, restricciones)
        viewModelScope.launch {
            apiService.registerUser(user).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    onResult(null)
                }
            })
        }
    }
}
