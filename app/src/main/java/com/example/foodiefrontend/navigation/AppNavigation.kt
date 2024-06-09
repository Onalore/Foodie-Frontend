package com.example.foodiefrontend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.presentation.ui.screens.home.HomeScreen
import com.example.foodiefrontend.presentation.ui.screens.login.LoginScreen
import com.example.foodiefrontend.presentation.ui.screens.profile.ProfileScreen
import com.example.foodiefrontend.presentation.ui.screens.recipes.RecipesScreen
import com.example.foodiefrontend.presentation.ui.screens.stock.StockScreen
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
        composable(route = AppScreens.RecipesScreen.route) {
            RecipesScreen(navController)
        }
        composable(route = AppScreens.StockScreen.route) {
            StockScreen(navController)
        }
        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}