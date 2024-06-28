package com.example.foodiefrontend.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.ApiErrorResponse
import com.example.foodiefrontend.data.EanResponse
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.StockConfirmationRequest
import com.example.foodiefrontend.service.BackendApi
import com.example.foodiefrontend.service.StockService
import com.example.foodiefrontend.utils.dataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class StockViewModel : ViewModel() {

    private val stockService: StockService = BackendApi.createStockService()

    private val _productType = MutableLiveData<Ingredient?>()
    val productType: LiveData<Ingredient?> get() = _productType

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _addProductResult = MutableLiveData<Boolean>()
    val addProductResult: LiveData<Boolean> get() = _addProductResult

    private val _stockIngredients = MutableLiveData<List<Ingredient>>()
    val stockIngredients: LiveData<List<Ingredient>> get() = _stockIngredients

    private val _stockResult = MutableLiveData<List<Ingredient>>()
    val stockResult: LiveData<List<Ingredient>> get() = _stockResult

    private val _confirmationResult = MutableLiveData<Boolean>()
    val confirmationResult: LiveData<Boolean> get() = _confirmationResult
    private val _tipoProducto = MutableLiveData<EanResponse?>()
    val tipoProducto: LiveData<EanResponse?> get() = _tipoProducto


    fun findProductByEan(ean: String) {
        viewModelScope.launch {
            try {
                Log.d("StockViewModel", "findProductByEan called with EAN: $ean")
                val response = stockService.findProductByEan(ean).awaitResponse()
                if (response.isSuccessful) {
                    val eanResponse = response.body()
                    val ingredient = eanResponse?.let {
                        Ingredient(
                            description = it.tipo ?: "",
                            unitMesure = it.unidadMedida ?: "",
                            imageUrl = it.imageUrl ?: ""
                        )
                    }
                    _productType.postValue(ingredient)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val apiErrorResponse = Gson().fromJson(errorBody, ApiErrorResponse::class.java)
                    Log.d("StockViewModel", "Error fetching product: ${apiErrorResponse.error}")
                    _error.postValue("No se pudo encontrar el producto: ${apiErrorResponse.error}")
                }
            } catch (e: Exception) {
                Log.d("StockViewModel", "Exception fetching product: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }

    fun obtenerTipoProductoPorEAN(ean: String) {
        viewModelScope.launch {
            try {
                Log.d("StockViewModel", "Obteniendo tipo de producto por EAN: $ean")
                val response = stockService.obtenerTipoProductoPorEAN(ean).awaitResponse()
                if (response.isSuccessful) {
                    _tipoProducto.postValue(response.body())
                } else {
                    Log.d(
                        "StockViewModel",
                        "Error al obtener tipo de producto: ${response.message()}"
                    )
                    _error.postValue("No se pudo obtener el tipo de producto: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("StockViewModel", "Exception al obtener tipo de producto: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }


    fun getUserStock(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                Log.d("StockViewModel", "Token obtained for getUserStock: $token")
                val response = stockService.getUserStock("Bearer $token").awaitResponse()
                if (response.isSuccessful) {
                    val stockIngredients = response.body()
                    val ingredients = mutableListOf<Ingredient>()
                    stockIngredients?.forEach { ingredientResponse ->
                        ingredients.add(
                            Ingredient(
                                id = ingredientResponse.id,
                                description = ingredientResponse.id,
                                quantity = ingredientResponse.cantidad.toString(),
                                unit = ingredientResponse.unidad,
                                unitMesure = ingredientResponse.unidadMedida,
                                imageUrl = ingredientResponse.imageUrl,
                                alertaEscasez = 0
                            )
                        )
                    }
                    _stockIngredients.postValue(ingredients)
                    Log.d("ingredientes", stockIngredients.toString())
                    Log.d("StockViewModel", "Stock fetched successfully")
                } else {
                    Log.e("StockViewModel", "Error fetching stock: ${response.message()}")
                }

            } else {
                Log.e("StockViewModel", "Token not found")
            }
        }
    }

    fun addProductByName(
        context: Context,
        nombreProducto: String,
        cantidad: Int,
        unidad: Int,
        alertaEscasez: Int
    ) {
        viewModelScope.launch {
            try {
                val token = getToken(context)
                Log.d("StockViewModel", "Token obtained for addProductByName: $token")
                val requestBody = mapOf(
                    "nombreProducto" to nombreProducto,
                    "cantidad" to cantidad,
                    "unidad" to unidad,
                    "alerta" to alertaEscasez
                )
                val response =
                    stockService.addProductByName(requestBody, "Bearer $token").awaitResponse()
                if (response.isSuccessful) {
                    _addProductResult.value = true
                    getUserStock(context) // Refresh stock ingredients if needed
                } else {
                    _addProductResult.value = false
                    Log.e("StockViewModel", "Error adding product by name: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Exception adding product by name: $e")
                _addProductResult.value = false
            }
        }
    }

    fun confirmUser(
        context: Context,
        codean: String,
        description: String,
        quantity: Int,
        unit: String,
        shortageAlert: Int,
        unitMesure: String
    ) {
        val stockConfirmationRequest = StockConfirmationRequest(
            codean, description, quantity, unit, shortageAlert, unitMesure
        )
        viewModelScope.launch {
            try {
                val token = getToken(context)
                Log.d("StockViewModel", "Token obtained for confirmUser: $token")
                val response =
                    stockService.confirmation("Bearer $token", stockConfirmationRequest)
                        .awaitResponse()
                if (response.isSuccessful) {
                    _confirmationResult.value = true
                    Log.d("StockViewModel", "User confirmation successful")
                } else {
                    _confirmationResult.value = false
                    Log.e("StockViewModel", "Error confirming user: ${response.message()}")
                }
            } catch (e: Exception) {
                _confirmationResult.value = false
                Log.e("StockViewModel", "Exception confirming user: $e")
            }
        }
    }

    private suspend fun getToken(context: Context): String? {
        val token = context.dataStore.data.first()[stringPreferencesKey("auth_token")]
        Log.d("StockViewModel", "Retrieved token: $token")
        return token
    }
}
