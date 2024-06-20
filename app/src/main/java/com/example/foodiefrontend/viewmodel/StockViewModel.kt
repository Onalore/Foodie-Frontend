package com.example.foodiefrontend.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.service.StockService
import com.example.foodiefrontend.service.BackendApi
import kotlinx.coroutines.launch
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

    fun addProductByEan(ean: String, cantidad: Int) {
        viewModelScope.launch {
            try {
                val requestBody = mapOf("ean" to ean, "cantidad" to cantidad)
                val response = stockService.addProductByEan(requestBody).awaitResponse()
                if (!response.isSuccessful) {
                    _error.value = "Error al agregar el producto: ${response.message()}"
                    _addProductResult.value = false
                } else {
                    _addProductResult.value = true
                    getUserStock() // Refresh stock ingredients if needed
                }
            } catch (e: Exception) {
                _error.value = "Error de excepción: ${e.message}"
                _addProductResult.value = false
            }
        }
    }

    fun getUserStock() {
        viewModelScope.launch {
            try {
                val response = stockService.getUserStock().awaitResponse()
                if (response.isSuccessful) {
                    _stockIngredients.postValue(response.body()?.ingredients ?: emptyList())
                } else {
                    Log.d("StockViewModel", "Error fetching stock: ${response.message()}")
                    _error.postValue("No se pudo obtener el stock: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("StockViewModel", "Exception fetching stock: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }

    private fun loadStockIngredients() {
        viewModelScope.launch {
            try {
                val response = stockService.getStockIngredients().awaitResponse()
                if (response.isSuccessful) {
                    val ingredients = response.body() ?: emptyList()
                    _stockIngredients.postValue(ingredients)
                } else {
                    _error.postValue("No se pudo cargar el stock: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }
}
