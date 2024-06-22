package com.example.foodiefrontend.navigation

sealed class AppScreens(val route: String) {


    object WelcomeScreen : AppScreens("welcome_screen")
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object HomeScreen : AppScreens("home_screen")
    object RecipesScreen : AppScreens("recipes_screen")
    object RecipeScreen : AppScreens("recipe_screen/{recipeJson}") {
        fun createRoute(recipeJson: String) = "recipe_screen/$recipeJson"
    }
    object SuggestedRecipesScreen : AppScreens("suggested_recipes_screen")
    object RandomRecipesScreen : AppScreens("random_recipes_screen")

    object StockScreen : AppScreens("stock_screen") {
        fun createRoute(codeEan: String?) = if (codeEan != null) "stock_screen/$codeEan" else "stock_screen"
    }

    object ProfileScreen : AppScreens("profile_screen")
    object CameraScreen : AppScreens("camera_screen")
    object FamilyConfigScreen : AppScreens("family_config_screen")
    object AddFamilyScreen : AppScreens("add_family_screen")
}
