package com.example.foodiefrontend.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.service.BackendApi
import com.example.foodiefrontend.service.StockService
import com.example.foodiefrontend.utils.dataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.awaitResponse

class StockViewModel : ViewModel() {

    private val stockService: StockService = BackendApi.createStockService()

    private val _productType = MutableLiveData<String>()
    val productType: LiveData<String> get() = _productType

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _addProductResult = MutableLiveData<Boolean>()
    val addProductResult: LiveData<Boolean> get() = _addProductResult

    private val _stockIngredients = MutableLiveData<List<Ingredient>>()
    val stockIngredients: LiveData<List<Ingredient>> get() = _stockIngredients

    private val _stockResult = MutableLiveData<List<Ingredient>>()
    val stockResult: LiveData<List<Ingredient>> get() = _stockResult

    fun findProductByEan(ean: String) {
        viewModelScope.launch {
            try {
                Log.d("StockViewModel", "findProductByEan called with EAN: $ean")
                val response = stockService.findProductByEan(ean).awaitResponse()
                if (response.isSuccessful) {
                    val eanResponse = response.body()
                    _productType.postValue(eanResponse?.tipo)
                } else {
                    Log.d("StockViewModel", "Error fetching product: ${response.message()}")
                    _error.postValue("No se pudo encontrar el producto: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("StockViewModel", "Exception fetching product: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }

    fun addProductByEan(ean: String, cantidad: Int, context: Context) {
        viewModelScope.launch {
            try {
                val token = getToken(context)
                Log.d("StockViewModel", "Token obtained for addProductByEan: $token")
                val requestBody = mapOf("ean" to ean, "cantidad" to cantidad)
                val response = stockService.addProductByEan(requestBody, "Bearer $token").awaitResponse()
                if (!response.isSuccessful) {
                    _error.value = "Error al agregar el producto: ${response.message()}"
                    _addProductResult.value = false
                } else {
                    _addProductResult.value = true
                    getUserStock(context) // Refresh stock ingredients if needed
                }
            } catch (e: Exception) {
                Log.d("StockViewModel", "Exception adding product: $e")
                _error.value = "Error de excepción: ${e.message}"
                _addProductResult.value = false
            }
        }
    }

    fun getUserStock(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                Log.d("StockViewModel", "Token obtained for getUserStock: $token")
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://10.0.2.2:8080/api/stock")
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                withContext(Dispatchers.IO) {
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val responseBody = response.body?.string()
                            val listType = object : TypeToken<List<Ingredient>>() {}.type
                            val stockIngredients: List<Ingredient> = Gson().fromJson(responseBody, listType)
                            _stockResult.postValue(stockIngredients)
                            _stockIngredients.postValue(stockIngredients) // Ensure stockIngredients is updated
                            Log.d("ingredientes", stockIngredients.toString())
                            Log.d("StockViewModel", "Stock fetched successfully")
                        } else {
                            Log.e("StockViewModel", "Error fetching stock: ${response.message}")
                        }
                    }
                }
            } else {
                Log.e("StockViewModel", "Token not found")
            }
        }
    }

    private suspend fun getToken(context: Context): String? {
        val token = context.dataStore.data.first()[stringPreferencesKey("auth_token")]
        Log.d("StockViewModel", "Retrieved token: $token")
        return token
    }
}
