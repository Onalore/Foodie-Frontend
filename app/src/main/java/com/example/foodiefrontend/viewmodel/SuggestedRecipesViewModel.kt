package com.example.foodiefrontend.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.service.BackendApi
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class SuggestedRecipesViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRecipes() {
        viewModelScope.launch {
            try {
                Log.d("com.example.foodiefrontend.viewmodel.SuggestedRecipesViewModel", "fetchRecipes called")
                val response = BackendApi.createRecipesService().getRecipes().awaitResponse()
                if (response.isSuccessful) {
                    val recipesList = response.body() ?: emptyList()
                    _recipes.postValue(recipesList)
                } else {
                    Log.d("com.example.foodiefrontend.viewmodel.SuggestedRecipesViewModel", "Error Recipes fetched: ${response.message()}")
                    _error.postValue("No se pudieron recuperar las recetas: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("com.example.foodiefrontend.viewmodel.SuggestedRecipesViewModel", "Catch Recipes fetched: $e")
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }
}
