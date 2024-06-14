package com.example.foodiefrontend.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.service.RecipesApi
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class RecipesResultViewModel : ViewModel() {
    private val _recipes = MutableLiveData<List<Recipe>?>()
    val recipes: MutableLiveData<List<Recipe>?> get() = _recipes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchRecipes() {
        viewModelScope.launch {
            try {
                val response = RecipesApi.create().getRecipes().awaitResponse()
                if (response.isSuccessful) {
                    val recipesResponse = response.body()
                    val recipesList = recipesResponse?.recipes ?: emptyList()
                    _recipes.postValue(recipesList)
                } else {
                    _error.postValue("No se pudieron recuperar las recetas: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error de excepción: ${e.message}")
            }
        }
    }
}
