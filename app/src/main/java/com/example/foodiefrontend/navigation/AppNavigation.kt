package com.example.foodiefrontend.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.presentation.ui.screens.camera.CameraScreen
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.AddFamilyScreen
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.FamilyConfigScreen
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.ModifyFamilyScreen
import com.example.foodiefrontend.presentation.ui.screens.home.HomeScreen
import com.example.foodiefrontend.presentation.ui.screens.home.suggestedRecipes.RandomRecipesScreen
import com.example.foodiefrontend.presentation.ui.screens.home.suggestedRecipes.SuggestedRecipesScreen
import com.example.foodiefrontend.presentation.ui.screens.login.LoginScreen
import com.example.foodiefrontend.presentation.ui.screens.profile.ProfileScreen
import com.example.foodiefrontend.presentation.ui.screens.recipe.RecipeScreen
import com.example.foodiefrontend.presentation.ui.screens.register.RegisterScreen
import com.example.foodiefrontend.presentation.ui.screens.stock.StockScreen
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
        composable(route = AppScreens.RegisterScreen.route) {
            Log.d("AppNavigation", "Navigating to RegisterScreen")
            RegisterScreen(navController)
        }
        composable(
            route = "${AppScreens.HomeScreen.route}/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            Log.d("AppNavigation", "Navigating to HomeScreen with username: $username")
            HomeScreen(navController, username)
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
        composable(route = AppScreens.SuggestedRecipesScreen.route) {
            SuggestedRecipesScreen(navController)
        }
        composable(route = AppScreens.RandomRecipesScreen.route) {
            RandomRecipesScreen(navController)
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
        composable(route = AppScreens.CameraScreen.route) {
            CameraScreen(navController) { codeEan ->
                navController.navigate("stock_screen/$codeEan")
            }
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
