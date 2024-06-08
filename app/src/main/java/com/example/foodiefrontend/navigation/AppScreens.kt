package com.example.foodiefrontend.navigation

sealed class AppScreens(val route: String) {
    object WelcomeScreen: AppScreens("welcome_screen")
    object LoginScreen: AppScreens("login_screen")
}