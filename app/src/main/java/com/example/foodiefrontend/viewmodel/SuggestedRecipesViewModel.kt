package com.example.foodiefrontend.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.DinersData
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.service.BackendApi
import com.example.foodiefrontend.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class SuggestedRecipesViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRecipes(context: Context, comensales: List<Persona>, comida: String) {
        viewModelScope.launch {
            try {
                val token = "Bearer " + getToken(context)
                Log.d("SuggestedRecipesViewModel", "fetchRecipes called with token $token")

                val dinersData =
                    DinersData(comensales, comida) // Ajusta la clase DinersData a tus necesidades
                val response =
                    BackendApi.createRecipesService().getRecipes(token, dinersData).awaitResponse()

                if (response.isSuccessful) {
                    val recipesList = response.body() ?: emptyList()
                    _recipes.postValue(recipesList)
                } else {
                    Log.d(
                        "SuggestedRecipesViewModel",
                        "Error Recipes fetched: ${response.message()}"
                    )
                    Log.d("SuggestedRecipesViewModel", "Error Recipes code: ${response.code()}")
                    Log.d(
                        "SuggestedRecipesViewModel",
                        "Error Recipes body: ${response.errorBody()?.string()}"
                    )
                    _error.postValue("No se pudieron recuperar las recetas: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("SuggestedRecipesViewModel", "Catch Recipes fetched: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }


    fun fetchRandomRecipes(context: Context, comensales: List<Persona>, comida: String) {
        viewModelScope.launch {
            try {
                val token = "Bearer " + getToken(context)
                Log.d("SuggestedRecipesViewModel", "fetchRecipes called with token $token")
                val dinersData = DinersData(comensales, comida)
                val response =
                    BackendApi.createRecipesService().randomRecipes(token, dinersData)
                        .awaitResponse()
                if (response.isSuccessful) {
                    val recipesList = response.body() ?: emptyList()
                    _recipes.postValue(recipesList)
                } else {
                    Log.d(
                        "SuggestedRecipesViewModel",
                        "Error Recipes fetched: ${response.message()}"
                    )
                    Log.d("SuggestedRecipesViewModel", "Error Recipes code: ${response.code()}")
                    Log.d(
                        "SuggestedRecipesViewModel",
                        "Error Recipes body: ${response.errorBody()?.string()}"
                    )
                    _error.postValue("No se pudieron recuperar las recetas: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("SuggestedRecipesViewModel", "Catch Recipes fetched: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }

    private suspend fun getToken(context: Context): String {
        return context.dataStore.data.first()[stringPreferencesKey("auth_token")] ?: ""
    }
}
