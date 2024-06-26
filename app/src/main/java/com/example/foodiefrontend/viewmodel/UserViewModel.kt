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
import com.example.foodiefrontend.data.FilterCriteria
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.LoginRequest
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.data.RatingData
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.data.RegisterResponse
import com.example.foodiefrontend.data.User
import com.example.foodiefrontend.service.BackendApi
import com.example.foodiefrontend.service.Config
import com.example.foodiefrontend.service.RecipesService
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
    private val apiRecipeService: RecipesService = BackendApi.createRecipesService()

    private val _loginResult = MutableLiveData<AuthResponse?>()
    val loginResult: LiveData<AuthResponse?> = _loginResult

    private val _userInfo = MutableLiveData<User?>()
    val userInfo: LiveData<User?> = _userInfo

    private val _registerResult = MutableLiveData<RegisterResponse?>()
    val registerResult: LiveData<RegisterResponse?> = _registerResult

    private val _stockResult = MutableLiveData<List<Ingredient>>()
    val stockResult: LiveData<List<Ingredient>> = _stockResult

    private val _familyMembers = MutableLiveData<List<Persona>>()
    val familyMembers: LiveData<List<Persona>> = _familyMembers

    private val _favoriteRecipes = MutableLiveData<List<Recipe>?>()
    val favoriteRecipes: MutableLiveData<List<Recipe>?> get() = _favoriteRecipes

    private val _temporaryRecipe = MutableLiveData<Recipe?>()
    val temporaryRecipe: MutableLiveData<Recipe?> get() = _temporaryRecipe

    private val _createdRecipes = MutableLiveData<List<Recipe>?>()
    val createdRecipes: LiveData<List<Recipe>?> = _createdRecipes

    private val _historyRecipes = MutableLiveData<List<Recipe>?>()
    val historyRecipes: LiveData<List<Recipe>?> = _historyRecipes

    private val _filteredRecipes = MutableLiveData<List<Recipe>?>()
    val filteredRecipes: LiveData<List<Recipe>?> = _filteredRecipes

    fun loginUser(context: Context, mail: String, password: String) {
        val loginRequest = LoginRequest(mail = mail, password = password)
        viewModelScope.launch {
            Log.d("UserViewModel", "Starting loginUser with email: $mail")
            apiService.loginUser(loginRequest).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
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
                        Log.d(
                            "UserViewModel",
                            "Login failed with response code: ${response.code()}"
                        )
                        Log.d("UserViewModel", "Response message: ${response.message()}")
                        Log.d(
                            "UserViewModel",
                            "Response error body: ${response.errorBody()?.string()}"
                        )
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
                    .url(Config.getBaseUrl() + "usuarios")
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

    fun getFamilyMembers(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                Log.d("UserViewModel", "Fetching family members with token: $token")
                apiService.getFamilyMembers("Bearer $token")
                    .enqueue(object : Callback<List<Persona>> {
                        override fun onResponse(
                            call: Call<List<Persona>>,
                            response: Response<List<Persona>>
                        ) {
                            if (response.isSuccessful) {
                                Log.d(
                                    "UserViewModel",
                                    "Family members fetched successfully: ${response.body()}"
                                )
                                _familyMembers.postValue(response.body())
                            } else {
                                Log.e(
                                    "UserViewModel",
                                    "Error fetching family members: ${response.message()}"
                                )
                                Log.e("UserViewModel", "Response code: ${response.code()}")
                                Log.e(
                                    "UserViewModel",
                                    "Response error body: ${response.errorBody()?.string()}"
                                )
                            }
                        }

                        override fun onFailure(call: Call<List<Persona>>, t: Throwable) {
                            Log.e("UserViewModel", "Failed to fetch family members: ${t.message}")
                        }
                    })
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun registerUser(
        mail: String,
        password: String,
        nombre: String,
        apellido: String,
        edad: Int,
        restricciones: List<String>,
        onResult: (RegisterResponse?) -> Unit
    ) {
        // Create Persona object
        val persona = Persona(nombre, apellido, edad, restricciones)
        // Create User object
        val user = User(mail, password, persona)

        // Logging data to ensure everything is passed correctly
        Log.d("UserViewModel", "Registering user with email: $mail")
        Log.d("UserViewModel", "Password: $password")
        Log.d("UserViewModel", "Nombre: $nombre")
        Log.d("UserViewModel", "Apellido: $apellido")
        Log.d("UserViewModel", "Edad: $edad")
        Log.d("UserViewModel", "Restricciones: $restricciones")

        // Convert User object to JSON for additional logging
        val gson = Gson()
        val userJson = gson.toJson(user)
        Log.d("UserViewModel", "User JSON: $userJson")

        // Check for empty fields before making the API call
        if (mail.isBlank() || password.isBlank() || nombre.isBlank() || apellido.isBlank() || edad <= 0) {
            Log.e("UserViewModel", "One or more fields are empty or invalid")
            onResult(null)
            return
        }

        // Make the API call to register the user
        viewModelScope.launch {
            apiService.registerUser(user).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("UserViewModel", "Registration successful")
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

    fun addFamilyMember(
        context: Context,
        nombre: String,
        apellido: String,
        edad: Int,
        restricciones: List<String>,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                val persona = Persona(
                    nombre = nombre,
                    apellido = apellido,
                    edad = edad,
                    restricciones = restricciones
                )
                apiService.addFamilyMember("Bearer $token", persona)
                    .enqueue(object : Callback<Persona> {
                        override fun onResponse(call: Call<Persona>, response: Response<Persona>) {
                            if (response.isSuccessful) {
                                Log.d(
                                    "UserViewModel",
                                    "Family member added successfully: ${response.body()}"
                                )
                                onResult(true)
                                getFamilyMembers(context) // Refresh the family members list
                            } else {
                                Log.e(
                                    "UserViewModel",
                                    "Error adding family member: ${response.message()}"
                                )
                                Log.e("UserViewModel", "Response code: ${response.code()}")
                                Log.e(
                                    "UserViewModel",
                                    "Response error body: ${response.errorBody()?.string()}"
                                )
                                onResult(false)
                            }
                        }

                        override fun onFailure(call: Call<Persona>, t: Throwable) {
                            Log.e("UserViewModel", "Failed to add family member: ${t.message}")
                            onResult(false)
                        }
                    })
            } else {
                Log.e("UserViewModel", "Token not found")
                onResult(false)
            }
        }
    }

    fun updateFamilyMember(context: Context, persona: Persona, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                Log.d("UserViewModel", "Retrieved token: $token")
                apiService.updateFamilyMember("Bearer $token", persona)
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                onResult(true)
                            } else {
                                Log.e(
                                    "UserViewModel",
                                    "Error updating family member: ${response.message()}"
                                )
                                onResult(false)
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("UserViewModel", "Update family member failed: ${t.message}")
                            onResult(false)
                        }
                    })
            } else {
                Log.e("UserViewModel", "Token not found")
                onResult(false)
            }
        }
    }

    fun deleteFamilyMember(context: Context, persona: Persona, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                apiService.deleteFamilyMember(
                    "Bearer $token",
                    persona.nombre ?: "",
                    persona.apellido ?: ""
                ).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            onResult(true)
                            getFamilyMembers(context) // Refresh the family members list
                        } else {
                            Log.e(
                                "UserViewModel",
                                "Error deleting family member: ${response.message()}"
                            )
                            onResult(false)
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("UserViewModel", "Delete family member failed: ${t.message}")
                        onResult(false)
                    }
                })
            } else {
                Log.e("UserViewModel", "Token not found")
                onResult(false)
            }
        }
    }

    fun logout(context: Context, onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            // Limpiar los datos del usuario (por ejemplo, SharedPreferences)
            val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

            // Notificar que el cierre de sesión está completo
            onLogoutComplete()
        }
    }

    fun fetchFavoriteRecipes(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                try {
                    val response = apiRecipeService.getFavoriteRecipes("Bearer $token")
                    Log.d("UserViewModel", "Request sent to /favoritas with token: Bearer $token")
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("UserViewModel", "Response body: $responseBody")

                        val recipes = responseBody?.recetas
                        if (recipes != null && recipes.isNotEmpty()) {
                            _favoriteRecipes.postValue(recipes)
                            Log.d("UserViewModel", "Favorite recipes fetched successfully")
                        } else {
                            Log.e("UserViewModel", "No recipes found in the response")
                        }
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Error fetching favorite recipes: ${response.code()} ${response.message()}"
                        )
                        Log.e("UserViewModel", "Error body: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error fetching favorite recipes: ${e.message}")
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun fetchTemporaryRecipe(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                try {
                    val response = apiRecipeService.getTemporaryRecipe("Bearer $token")
                    Log.d("UserViewModel", "Request sent to /ver with token: Bearer $token")
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("UserViewModel", "Response body: $responseBody")

                        val recipe = responseBody?.recetaTemporal
                        if (recipe != null) {
                            _temporaryRecipe.postValue(recipe)
                            Log.d("UserViewModel", "Temporal recipe fetched successfully")
                        } else {
                            Log.e("UserViewModel", "No temporal recipe found in the response")
                        }
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Error fetching favorite recipes: ${response.code()} ${response.message()}"
                        )
                        Log.e("UserViewModel", "Error body: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error fetching favorite recipes: ${e.message}")
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun rateRecipe(context: Context, puntuacion: Int, favorita: Boolean) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                try {
                    val ratingData = RatingData(puntuacion, favorita)
                    val response = apiRecipeService.rateRecipe("Bearer $token", ratingData)
                    if (response.isSuccessful) {
                        Log.d("UserViewModel", "Recipe rated successfully")
                        fetchTemporaryRecipe(context) // Refresh temporary recipe
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Error rating recipe: ${response.code()} ${response.message()}"
                        )
                        Log.e("UserViewModel", "Error body: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error rating recipe: ${e.message}")
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun deleteTemporaryRecipe(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                try {
                    val response = apiRecipeService.deleteTemporaryRecipe("Bearer $token")
                    if (response.isSuccessful) {
                        Log.d("UserViewModel", "Temporary recipe deleted successfully")
                        fetchTemporaryRecipe(context) // Refresh temporary recipe
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Error deleting temporary recipe: ${response.code()} ${response.message()}"
                        )
                        Log.e("UserViewModel", "Error body: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error deleting temporary recipe: ${e.message}")
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun fetchCreatedRecipes(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                try {
                    val response = apiRecipeService.getCreatedRecipes("Bearer $token")
                    if (response.isSuccessful) {
                        _createdRecipes.postValue(response.body()?.recetas)
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Error fetching created recipes: ${response.message()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error fetching created recipes: ${e.message}")
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun fetchHistoryRecipes(context: Context) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                try {
                    val response = apiRecipeService.getHistoryRecipes("Bearer $token")
                    if (response.isSuccessful) {
                        _historyRecipes.postValue(response.body()?.recetas)
                    } else {
                        Log.e(
                            "UserViewModel",
                            "Error fetching history recipes: ${response.message()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error fetching history recipes: ${e.message}")
                }
            } else {
                Log.e("UserViewModel", "Token not found")
            }
        }
    }

    fun filterRecipes(recipes: List<Recipe>?, criteria: FilterCriteria) {
        if (recipes == null) return

        Log.d("UserViewModel", "Filter criteria: $criteria")
        Log.d("UserViewModel", "Recipes before filtering: ${recipes.size}")

        recipes.forEach { recipe ->
            Log.d("UserViewModel", "Recipe: ${recipe.name}, Rating: ${recipe.rating}")
        }

        val filtered = recipes.filter { recipe ->
            criteria.minRating?.let { minRating ->
                recipe.rating >= minRating
            } ?: true
        }

        Log.d("UserViewModel", "Recipes after filtering: ${filtered.size}")
        _filteredRecipes.postValue(filtered)
    }

    fun clearFilteredRecipes() {
        _filteredRecipes.postValue(null)
        Log.d("UserViewModel", "Filtered recipes cleared")
    }

    fun saveTemporaryRecipe(context: Context, recipe: Recipe, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                apiRecipeService.saveTemporaryRecipe("Bearer $token", recipe)
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful) {
                                Log.d("UserViewModel", "Temporary recipe saved successfully")
                                onResult(true)
                            } else {
                                Log.e(
                                    "UserViewModel",
                                    "Error saving temporary recipe: ${response.message()}"
                                )
                                onResult(false)
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.e("UserViewModel", "Error saving temporary recipe: ${t.message}")
                            onResult(false)
                        }
                    })
            } else {
                Log.e("UserViewModel", "Token not found")
                onResult(false)
            }
        }
    }

    fun createRecipe(context: Context, recipe: Recipe, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val token = getToken(context)
            if (token != null) {
                val recipeJson = Gson().toJson(recipe)
                Log.d("UserViewModel", "Recipe JSON: $recipeJson")
                apiRecipeService.createRecipe("Bearer $token", recipe)
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful) {
                                Log.d("UserViewModel", "Recipe created successfully")
                                onResult(true)
                            } else {
                                Log.e(
                                    "UserViewModel",
                                    "Error creating recipe: ${response.message()}"
                                )
                                onResult(false)
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.e("UserViewModel", "Error creating recipe: ${t.message}")
                            onResult(false)
                        }
                    })
            } else {
                Log.e("UserViewModel", "Token not found")
                onResult(false)
            }
        }
    }
}



