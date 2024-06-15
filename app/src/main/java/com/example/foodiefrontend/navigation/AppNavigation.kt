package com.example.foodiefrontend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.ui.screens.camera.CameraScreen
import com.example.foodiefrontend.presentation.ui.screens.home.HomeScreen
import com.example.foodiefrontend.presentation.ui.screens.login.LoginScreen
import com.example.foodiefrontend.presentation.ui.screens.profile.ProfileScreen
import com.example.foodiefrontend.presentation.ui.screens.recipe.RecipeScreen
import com.example.foodiefrontend.presentation.ui.screens.recipes.RecipesScreen
import com.example.foodiefrontend.presentation.ui.screens.stock.StockScreen
import com.example.foodiefrontend.presentation.ui.screens.suggestedRecipes.SuggestedRecipesScreen
import com.example.foodiefrontend.presentation.ui.screens.welcome.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = AppScreens.WelcomeScreen.route
    ) {
        composable(route = AppScreens.WelcomeScreen.route) {
            WelcomeScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(navController)
        }
        //Plural
        composable(route = AppScreens.RecipesScreen.route) {
            RecipesScreen(navController, SampleData.recipes)
        }
        //Singular
        composable(route = AppScreens.RecipeScreen.route) {
            RecipeScreen(navController, SampleData.recipe)
        }
        composable(route = AppScreens.SuggestedRecipesScreen.route) {
            SuggestedRecipesScreen(navController)
        }
        // Route without codeEan
        composable(route = AppScreens.StockScreen.route) {
            StockScreen(navController, null)
        }
        // Route with codeEan
        composable(
            route = "stock_screen/{codeEan}",
            arguments = listOf(navArgument("codeEan") { type = NavType.StringType })
        ) { backStackEntry ->
            val codeEan = backStackEntry.arguments?.getString("codeEan")
            StockScreen(navController, codeEan)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(route = AppScreens.CameraScreen.route) {
            CameraScreen(navController, navigateToScreen = { codeEan ->
                navController.navigate(AppScreens.StockScreen.createRoute(codeEan))
            })
        }
    }
}