package com.example.foodiefrontend.navigation

import SuggestedRecipesScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.presentation.ui.screens.camera.CameraScreen
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.AddFamilyScreen
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.FamilyConfigScreen
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.ModifyFamilyScreen
import com.example.foodiefrontend.presentation.ui.screens.home.HomeScreen
import com.example.foodiefrontend.presentation.ui.screens.home.components.AlertScore
import com.example.foodiefrontend.presentation.ui.screens.home.suggestedRecipes.RandomRecipesScreen
import com.example.foodiefrontend.presentation.ui.screens.login.LoginScreen
import com.example.foodiefrontend.presentation.ui.screens.profile.ProfileScreen
import com.example.foodiefrontend.presentation.ui.screens.recipe.RecipeScreen
import com.example.foodiefrontend.presentation.ui.screens.recipes.RecipesScreen
import com.example.foodiefrontend.presentation.ui.screens.recipes.newRecipe.NewRecipeScreen
import com.example.foodiefrontend.presentation.ui.screens.register.RegisterScreen
import com.example.foodiefrontend.presentation.ui.screens.register.SuccessfulRegisterScreen
import com.example.foodiefrontend.presentation.ui.screens.stock.StockScreen
import com.example.foodiefrontend.presentation.ui.screens.stock.components.AlertIngredient
import com.example.foodiefrontend.presentation.ui.screens.welcome.WelcomeScreen
import com.example.foodiefrontend.viewmodel.UserViewModel
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.WelcomeScreen.route
    ) {
        composable(route = AppScreens.WelcomeScreen.route) {
            Log.d("AppNavigation", "Navigating to WelcomeScreen")
            WelcomeScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route) {
            Log.d("AppNavigation", "Navigating to LoginScreen")
            LoginScreen(navController)
        }
        composable(route = AppScreens.SuccessfulRegisterScreen.route) {
            Log.d("AppNavigation", "Navigating to SuccessfulRegisterScreen")
            SuccessfulRegisterScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            Log.d("AppNavigation", "Navigating to RegisterScreen")
            RegisterScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route) {
            Log.d("AppNavigation", "Navigating to HomeScreen")
            HomeScreen(navController)
        }
        composable(AppScreens.RateRecipeScreen.route) { backStackEntry ->
            val recipeJson = backStackEntry.arguments?.getString("recipeJson")
            val recipe = Gson().fromJson(recipeJson, Recipe::class.java)
            var showDialog by remember { mutableStateOf(true) }

            if (showDialog) {
                Log.d("AppNavigation", "Showing AlertScore for recipe: $recipe")
                AlertScore(
                    navController = navController,
                    recipe = recipe,
                    setShowDialog = { showDialog = it },
                    userViewModel = viewModel()
                )
            }
        }
        composable(
            route = AppScreens.RecipeScreen.route,
            arguments = listOf(navArgument("recipeJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedRecipeJson = backStackEntry.arguments?.getString("recipeJson") ?: ""
            val recipeJson = URLDecoder.decode(encodedRecipeJson, StandardCharsets.UTF_8.toString())
            val recipe = Gson().fromJson(recipeJson, Recipe::class.java)
            Log.d("Navigation", "Navigating to RecipeScreen with recipe: $recipe")
            RecipeScreen(navController, recipe)
        }
        composable(
            route = AppScreens.SuggestedRecipesScreen.route,
            arguments = listOf(navArgument("comensales") { type = NavType.StringType },
                navArgument("comida") { type = NavType.StringType })
        ) { entry ->
            val comensalesJson = entry.arguments?.getString("comensales") ?: "[]"
            val comensales = Gson().fromJson(
                URLDecoder.decode(
                    comensalesJson,
                    StandardCharsets.UTF_8.toString()
                ), Array<Persona>::class.java
            ).toList()
            val comida = entry.arguments?.getString("comida") ?: ""
            SuggestedRecipesScreen(navController, comensales, comida)
        }
        composable(
            route = AppScreens.RandomRecipesScreen.route,
            arguments = listOf(navArgument("comensales") { type = NavType.StringType },
                navArgument("comida") { type = NavType.StringType })
        ) { entry ->
            val comensalesJson = entry.arguments?.getString("comensales") ?: "[]"
            val comensales = Gson().fromJson(
                URLDecoder.decode(
                    comensalesJson,
                    StandardCharsets.UTF_8.toString()
                ), Array<Persona>::class.java
            ).toList()
            val comida = entry.arguments?.getString("comida") ?: ""
            RandomRecipesScreen(navController, comensales, comida)
        }
        composable(route = AppScreens.RecipesScreen.route) {
            RecipesScreen(navController, userViewModel)
        }
        composable(AppScreens.NewRecipeScreen.route) {
            NewRecipeScreen(navController, userViewModel)
        }
        composable(route = AppScreens.StockScreen.route) {
            StockScreen(navController)
        }
        composable(
            route = "stock_screen/{codeEan}",
            arguments = listOf(navArgument("codeEan") { type = NavType.StringType })
        ) { backStackEntry ->
            val codeEan = backStackEntry.arguments?.getString("codeEan")
            StockScreen(navController, codeEan)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController, userViewModel, context)
        }
        composable("camera_screen") {
            CameraScreen(navController) { ean ->
                navController.navigate("alertIngredientScanned/$ean")
            }
        }
        composable("alertIngredientScanned/{ean}") { backStackEntry ->
            val ean = backStackEntry.arguments?.getString("ean") ?: ""
            AlertIngredient(
                navController,
                setShowDialog = { /* Implementar lógica */ },
                codeEan = ean
            )
        }
        composable(
            route = "alertIngredient/{ingredientJson}",
            arguments = listOf(navArgument("ingredientJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val ingredientJson = backStackEntry.arguments?.getString("ingredientJson") ?: ""
            val ingredient = Gson().fromJson(
                URLDecoder.decode(
                    ingredientJson,
                    StandardCharsets.UTF_8.toString()
                ), Ingredient::class.java
            )
            AlertIngredient(
                navController = navController,
                setShowDialog = { /* Implement your logic here */ },
                tipoProducto = ingredient,
                codeEan = ""
            )
        }

        composable(route = AppScreens.FamilyConfigScreen.route) {
            FamilyConfigScreen(navController, userViewModel)
        }
        composable(route = AppScreens.AddFamilyScreen.route) {
            AddFamilyScreen(navController)
        }
        composable(
            route = AppScreens.ModifyFamilyScreen.route,
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("apellido") { type = NavType.StringType },
                navArgument("edad") { type = NavType.IntType },
                navArgument("restricciones") {
                    type = NavType.StringType; nullable = true; defaultValue = ""
                }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern =
                    "android-app://androidx.navigation/modify_family_screen/{nombre}/{apellido}/{edad}?restricciones={restricciones}"
            })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val apellido = backStackEntry.arguments?.getString("apellido") ?: ""
            val edad = backStackEntry.arguments?.getInt("edad") ?: 0
            val restricciones =
                backStackEntry.arguments?.getString("restricciones")?.split(",") ?: emptyList()
            ModifyFamilyScreen(
                navController = navController,
                initialNombre = nombre,
                initialApellido = apellido,
                initialEdad = edad,
                initialRestricciones = restricciones
            )
        }
    }
}
