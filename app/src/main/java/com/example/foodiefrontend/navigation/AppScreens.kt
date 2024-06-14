package com.example.foodiefrontend.navigation

sealed class AppScreens(val route: String) {
    object WelcomeScreen: AppScreens("welcome_screen")
    object LoginScreen: AppScreens("login_screen")
    object HomeScreen: AppScreens("home_screen")
    object RecipesScreen: AppScreens("recipes_screen")
    object RecipeScreen: AppScreens("recipe_screen")
    object SuggestedRecipesScreen: AppScreens("suggested_recipes_screen")
    object StockScreen: AppScreens("stock_screen") {
        fun createRoute(codeEan: String?) = if (codeEan != null) "stock_screen/$codeEan" else "stock_screen"
    }
    object ProfileScreen: AppScreens("profile_screen")
    object CameraScreen: AppScreens("camera_screen")
}