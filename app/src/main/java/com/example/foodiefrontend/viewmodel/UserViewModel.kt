package com.example.foodiefrontend.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.AuthResponse
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.LoginRequest
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.data.RegisterResponse
import com.example.foodiefrontend.data.StockResponse
import com.example.foodiefrontend.data.User
import com.example.foodiefrontend.service.BackendApi
import com.example.foodiefrontend.service.UserService
import com.example.foodiefrontend.utils.dataStore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val apiService: UserService = BackendApi.createUserService()

    private val _loginResult = MutableLiveData<AuthResponse?>()
    val loginResult: LiveData<AuthResponse?> = _loginResult

    private val _userInfo = MutableLiveData<User?>()
    val userInfo: LiveData<User?> = _userInfo

    private val _registerResult = MutableLiveData<RegisterResponse?>()
    val registerResult: LiveData<RegisterResponse?> = _registerResult

    private val _stockResult = MutableLiveData<List<Ingredient>>()
    val stockResult: LiveData<List<Ingredient>> = _stockResult

    fun loginUser(context: Context, mail: String, password: String) {
        val loginRequest = LoginRequest(mail = mail, password = password)
        viewModelScope.launch {
            Log.d("UserViewModel", "Starting loginUser with email: $mail")
            apiService.loginUser(loginRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()
                        Log.d("UserViewModel", "Login successful: $authResponse")
                        _loginResult.value = authResponse
                        authResponse?.token?.let { token ->
                            Log.d("UserViewModel", "Storing token: $token")
                            viewModelScope.launch {
                                storeToken(context, token)
                            }
                        }
                    } else {
                        Log.d("UserViewModel", "Login failed with response code: ${response.code()}")
                        Log.d("UserViewModel", "Response message: ${response.message()}")
                        Log.d("UserViewModel", "Response error body: ${response.errorBody()?.string()}")
                        _loginResult.value = null
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.e("UserViewModel", "Login failed with error: ${t.message}")
                    _loginResult.value = null
                }
            })
        }
    }

    private suspend fun storeToken(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("auth_token")] = token
        }
        Log.d("UserViewModel", "Token stored: $token")
    }

    private suspend fun getToken(context: Context): String? {
        val token = context.dataStore.data.first()[stringPreferencesKey("auth_token")]
        Log.d("UserViewModel", "Retrieved token: $token")
        return token
    }

    fun getUserInfo(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://10.0.2.2:8080/api/usuarios")
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                withContext(Dispatchers.IO) {
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val responseBody = response.body?.string()
                            val user = Gson().fromJson(responseBody, User::class.java)
                            _userInfo.postValue(user)
                            Log.d("UserViewModel", "User info fetched successfully")
                        } else {
                            Log.e("UserViewModel", "Error fetching user info: ${response.message}")
                        }
                    }
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun getUserStock(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://10.0.2.2:8080/api/stock")
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                withContext(Dispatchers.IO) {
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val responseBody = response.body?.string()
                            val stockResponse = Gson().fromJson(responseBody, StockResponse::class.java)
                            _stockResult.postValue(stockResponse.ingredients)
                            Log.d("UserViewModel", "Stock fetched successfully")
                        } else {
                            Log.e("UserViewModel", "Error fetching stock: ${response.message}")
                        }
                    }
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun registerUser(mail: String, password: String, nombre: String, apellido: String, edad: Int, restricciones: List<String>, onResult: (RegisterResponse?) -> Unit) {
        Log.d("UserViewModel", "Registering user with email: $mail")
        Log.d("UserViewModel", "Password: $password")
        Log.d("UserViewModel", "Nombre: $nombre")
        Log.d("UserViewModel", "Apellido: $apellido")
        Log.d("UserViewModel", "Edad: $edad")
        Log.d("UserViewModel", "Restricciones: $restricciones")

        val persona = Persona(nombre, apellido, edad, restricciones)
        val user = User(mail, password, persona)

        val gson = Gson()
        val userJson = gson.toJson(user)
        Log.d("UserViewModel", "User JSON: $userJson")

        // Verificar que todos los campos obligatorios no estén vacíos
        if (mail.isBlank() || password.isBlank() || nombre.isBlank() || apellido.isBlank() || edad <= 0) {
            Log.e("UserViewModel", "Validation failed: Some fields are blank or invalid")
            onResult(null)
            return
        }

        viewModelScope.launch {
            apiService.registerUser(user).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        Log.d("UserViewModel", "User registered successfully: ${response.body()}")
                        onResult(response.body())
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Registration failed with response code: ${response.code()}"
                        )
                        Log.e("UserViewModel", "Response message: ${response.message()}")
                        Log.e(
                            "UserViewModel",
                            "Response error body: ${response.errorBody()?.string()}"
                        )
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.e("UserViewModel", "Registration failed with error: ${t.message}")
                    onResult(null)
                }
            })
        }
    }
}
